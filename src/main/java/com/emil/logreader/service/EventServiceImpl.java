package com.emil.logreader.service;

import com.emil.logreader.entity.Event;
import com.emil.logreader.entity.LogEntry;
import com.emil.logreader.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

    private final long acceptableEventDuration;

    private EventRepository eventRepository;

    public EventServiceImpl(@Value("${log.reader.event.duration}") long acceptableEventDuration, EventRepository eventRepository) {
        this.acceptableEventDuration = acceptableEventDuration;
        this.eventRepository = eventRepository;
    }

    @Override
    public void generateEvent(Pair<LogEntry, LogEntry> logEntryPair) {

        long eventDuration = Math.abs(logEntryPair.getFirst().getTimestamp() - logEntryPair.getSecond().getTimestamp());
        Event event = Event.builder()
                .id(logEntryPair.getFirst().getId())
                .duration(eventDuration)
                .alert(shouldBeAlerted(eventDuration))
                .host(logEntryPair.getFirst().getHost())
                .type(logEntryPair.getFirst().getType())
                .build();

        eventRepository.save(event);
        log.debug("Event was saved: {}", event);
    }

    private boolean shouldBeAlerted(long eventDuration) {
        return eventDuration > acceptableEventDuration;
    }
}
