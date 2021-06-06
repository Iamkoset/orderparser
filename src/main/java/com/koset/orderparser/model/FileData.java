package com.koset.orderparser.model;

import lombok.Builder;
import lombok.Value;

import java.nio.file.Path;

@Value
@Builder
public class FileData {

    Path filePath;

    FileFormat fileFormat;

}
