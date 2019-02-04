package com.emil.logreader.service;

import com.emil.logreader.aop.LogExecutionTime;
import com.emil.logreader.entity.LogEntry;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class ReaderServiceImpl implements ReaderService {

    private ObjectMapper objectMapper;

    private EventService eventService;

    public ReaderServiceImpl(ObjectMapper objectMapper, EventService eventService) {
        this.objectMapper = objectMapper;
        this.eventService = eventService;
    }

    @Override
    @LogExecutionTime
    public void readLogFile(Path filePath){

        try (BufferedReader br = Files.newBufferedReader(filePath)) {

            Map<String, LogEntry> logEntriesWithoutPair = new ConcurrentHashMap<>();

            log.info("Reading logs from file: {}", filePath);
            br.lines().parallel()
                    .map(this::parseLine)
                    .filter(logEntry -> !StringUtils.isEmpty(logEntry.getId()))
                    .forEach(logEntry -> processLog(logEntry, logEntriesWithoutPair));

        } catch (IOException e) {
            log.error("Error processing file", e);
            throw new RuntimeException("Failed to process file", e);
        }
    }

    private LogEntry parseLine(String line) {
        try {
            log.debug("Parsing json log {} into POJO", line);
            return objectMapper.readValue(line, LogEntry.class);
        } catch (Exception e) {
            log.warn("Could not parse line {}", line, e);
            return LogEntry.builder().build();
        }
    }

    private void processLog(LogEntry logEntry, Map<String, LogEntry> logsWithoutPair) {

        log.debug("Searching for log pair for log id: {}", logEntry.getId());
        LogEntry pairEntry = logsWithoutPair.get(logEntry.getId());
        if (pairEntry != null) {
            eventService.generateEvent(Pair.of(logEntry, pairEntry));
            logsWithoutPair.remove(logEntry.getId());
        } else {
            logsWithoutPair.put(logEntry.getId(), logEntry);
        }
    }


//    public void readLogFileUsingDB(Path filePath) {
//
    //        ForkJoinPool forkJoinPool = new ForkJoinPool(6);
//        try {
//            List<LogEntry> logEntries = forkJoinPool.submit(() -> task(filePath)).get();
//        } catch (InterruptedException | ExecutionException e) {
//            // handle exceptions
//        } finally {
//            forkJoinPool.shutdown();
//        }
//        long start = System.nanoTime();
//        try (BufferedReader br = Files.newBufferedReader(filePath)) {
//
//            //br returns as stream and convert it into a List
//            br.lines().parallel()
//                    .map(this::parseLine).forEach(logEntry -> {
//
//                Optional<LogEntry> pairEntryOptional = logEntryRepository.findById(logEntry.getId());
//                if (pairEntryOptional.isPresent()) {
//
//                    LogEntry pairEntry = pairEntryOptional.get();
//
//                    long eventDuration = Math.abs(logEntry.getTimestamp() - pairEntry.getTimestamp());
//                    Event event = Event.builder()
//                            .id(logEntry.getId())
//                            .duration(eventDuration)
//                            .alert(eventDuration > 4).build();
//
//                    eventRepository.save(event);
//                    logEntryRepository.delete(pairEntry);
//                } else {
////                    log.info("Put: " + logEntry.getId());
//                    logEntryRepository.save(logEntry);
//                }
//            });
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        long end = System.nanoTime();
//
//        long duration = (end - start) / 1_000_000;
//        log.debug("Duration using DB: " + duration);
//
//    }

}
