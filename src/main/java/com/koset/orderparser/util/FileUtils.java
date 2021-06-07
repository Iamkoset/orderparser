package com.koset.orderparser.util;

import com.koset.orderparser.service.exception.ServiceException;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.Optional;

@UtilityClass
public class FileUtils {

    private static final String DOT = ".";

    public static String getFormat(String filePath) {
        return Optional.ofNullable(filePath)
                .filter(f -> f.contains(DOT))
                .map(f -> f.substring(filePath.lastIndexOf(DOT) + 1))
                .orElseThrow(
                        () -> new ServiceException("No file extension found: " + filePath)
                );
    }

    public static String resolveAbsolutePath(String filePath) {
        boolean isAbsolute = new File(filePath).isAbsolute();
        return isAbsolute
                ? filePath
                : Paths.get("").toAbsolutePath() + FileSystems.getDefault().getSeparator() + filePath;
    }

}
