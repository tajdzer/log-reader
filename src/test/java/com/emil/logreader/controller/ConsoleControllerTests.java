package com.emil.logreader.controller;

import com.emil.logreader.service.ReaderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ConsoleControllerTests {

    @Mock
    private ReaderService readerService;

    @InjectMocks
    private ConsoleController consoleController;


    @Test
    void shouldNotRun() {

        consoleController.run();

        verify(readerService, times(0)).readLogFile(any());
    }

    @Test
    void shouldRun() {

        consoleController.run("example file path");

        verify(readerService, times(1)).readLogFile(any());

    }

}
