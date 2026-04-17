package com.eventostec.api.domain.event;

import com.eventostec.api.domain.address.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Using lombok to automatically define Getters and Setters using Annotations

import java.util.Date;
import java.util.UUID;

//Using @Table and @Entity Annotation do link this class as the table on the database
//with the JpaRepository

@Table(name = "events")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue
    private UUID id;

    private String title;
    private String description;
    private String imgUrl;
    private  String eventUrl;
    private  Boolean remote;
    private Date date;

    @OneToOne(mappedBy = "event", cascade = CascadeType.ALL)
    private Address address;
    }
