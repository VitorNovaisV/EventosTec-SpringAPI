package com.eventostec.api.domain.address;


import com.eventostec.api.domain.event.Event;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Using lombok to automatically define Getters and Setters using Annotations

import java.util.UUID;

//Using @Table and @Entity Annotation do link this class as the table on the database
//with the JpaRepository

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue
    private UUID id;

    private String city;

    private String uf;

    @ManyToOne
    @JoinColumn(name = "events_ids")
    private Event event;

}
