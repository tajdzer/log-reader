package com.emil.logreader.repository;

import com.emil.logreader.entity.Event;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class EventRepositoryTests {

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void shouldSaveAndRetrieve() {

        String id = UUID.randomUUID().toString();
        eventRepository.save(Event.builder().id(id).build());
        Optional<Event> one = eventRepository.findById(id);

        assertThat(one).isNotEmpty();
        assertThat(id).isEqualTo(one.get().getId());


    }
}
