package com.koset.orderparser;

import com.koset.orderparser.model.FileData;
import com.koset.orderparser.model.FileFormat;
import com.koset.orderparser.service.reader.OrderReaderDelegate;
import com.koset.orderparser.service.writer.OrderWriter;
import com.koset.orderparser.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableConfigurationProperties
@RequiredArgsConstructor
public class DemoApplication implements CommandLineRunner {

    private final ExecutorService readerExecutor;

    private final ExecutorService writerExecutor;

    private final OrderReaderDelegate orderReaderDelegate;

    private final OrderWriter orderWriter;

    public static void main(String[] args) {
        new SpringApplicationBuilder(DemoApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }

    @Override
    public void run(String... filePaths) throws ExecutionException, InterruptedException {
        List<CompletableFuture<Void>> collect = Arrays.stream(filePaths)
                .map(this::buildFileData)
                .map(this::processFileData)
                .collect(Collectors.toList());

        for (CompletableFuture<Void> future : collect) {
            future.get(); // ignored, simply waiting for tasks to complete
        }

        readerExecutor.shutdown();
        writerExecutor.shutdown();
    }

    private FileData buildFileData(String filePath) {
        String resolvedPath = FileUtils.resolveAbsolutePath(filePath);

        return FileData.builder()
                .filePath(Paths.get(resolvedPath))
                .fileFormat(FileFormat.fromString(FileUtils.getFormat(resolvedPath)))
                .build();
    }

    private CompletableFuture<Void> processFileData(FileData fileData) {
        return CompletableFuture
                .supplyAsync(() -> orderReaderDelegate.read(fileData), readerExecutor)
                .thenAcceptAsync(orderWriter::write, writerExecutor);
    }
}
