package com.emil.logreader.controller;


import com.emil.logreader.service.ReaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@Controller
@Slf4j
public class ConsoleController implements CommandLineRunner {

    private ReaderService readerService;

    public ConsoleController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @Override
    public void run(String... args) {
        log.info("App Running with arguments: " + Arrays.toString(args));

        if (args.length > 0) {
            Path path = Paths.get(args[0]);
            readerService.readLogFile(path);
        } else {
            log.error("Please provide file path as program argument");
        }
    }
}
