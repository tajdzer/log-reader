package com.emil.logreader.service;

import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public interface ReaderService {
    void readLogFile(Path filePath);
}
