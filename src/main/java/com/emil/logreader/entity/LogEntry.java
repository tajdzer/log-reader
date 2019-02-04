package com.emil.logreader.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LogEntries")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class LogEntry {

    @Id
    private String id;
    private State state;
    private String type;
    private String host;
    private long timestamp;

}
