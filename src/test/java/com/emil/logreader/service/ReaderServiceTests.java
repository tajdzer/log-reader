package com.emil.logreader.service;

import com.emil.logreader.entity.Event;
import com.emil.logreader.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ReaderServiceTests {

    @Autowired
    private ReaderService readerService;

    @Autowired
    private EventRepository eventRepository;

    @Test
    void failToReadNotExistingFile() {

        Path path = Paths.get("not_existing_file");

        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> readerService.readLogFile(path))
                .withMessage("Failed to process file");
    }

    @Test
    void processFileAfterParsingLineException() {

        Path path = Paths.get("src/test/resources/test_files/parse_line_exception.log");

        assertThatCode(() -> {
            readerService.readLogFile(path);
        }).doesNotThrowAnyException();

    }

    @Test
    void processFileWithoutExceptions() {

        Path path = Paths.get("src/test/resources/test_files/2_events.log");

        readerService.readLogFile(path);
        Iterable<Event> events = eventRepository.findAll();

        assertThat(events).hasSize(2);

    }

}
