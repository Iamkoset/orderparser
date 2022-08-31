package com.koset.orderparser.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "executor")
public class ExecutorProperties {

    private int readerThreadsCount;

    private int writerThreadsCount;

}
