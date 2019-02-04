package com.emil.logreader.repository;

import com.emil.logreader.entity.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, String> {
}
