package com.emil.logreader.service;

import com.emil.logreader.entity.LogEntry;
import org.springframework.data.util.Pair;

public interface EventService {
    void generateEvent(Pair<LogEntry, LogEntry> logEntryPair);
}
