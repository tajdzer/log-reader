package com.emil.logreader.service;

import com.emil.logreader.entity.Event;
import com.emil.logreader.entity.LogEntry;
import com.emil.logreader.entity.State;
import com.emil.logreader.repository.EventRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.util.Pair;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EventServiceTests {

    @Value("${log.reader.event.duration}")
    private long acceptableEventDuration;

    @Autowired
    private EventRepository eventRepository;

    private EventService eventService;

    @BeforeAll
    void beforeAll() {
        this.eventService = new EventServiceImpl(acceptableEventDuration, eventRepository);
    }

    @Test
    void shouldProcessOneEvent() {

        String eventId = UUID.randomUUID().toString();

        LogEntry firstLogEntry = LogEntry.builder().id(eventId).state(State.STARTED).timestamp(System.currentTimeMillis()).build();
        LogEntry secondLogEntry = LogEntry.builder().id(eventId).state(State.FINISHED).timestamp(System.currentTimeMillis()).build();

        eventService.generateEvent(Pair.of(firstLogEntry, secondLogEntry));

        Optional<Event> event = eventRepository.findById(eventId);

        assertSoftly(
                softAssertions -> {
                    softAssertions.assertThat(event).isNotEmpty();
                    softAssertions.assertThat(event.get().getId()).isEqualTo(eventId);
                }
        );
    }


}
