package com.emil.logreader.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "events")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Event {

    @Id
    private String id;

    private long duration;

    private String type;

    private String host;

    private boolean alert;


}
