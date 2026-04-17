package com.eventostec.api.domain.coupon;

import com.eventostec.api.domain.event.Event;
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
//When the table in the database has a different name compared to the class,
//the "name" attribute must be specified

@Entity
@Table(name = "coupon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue
    private UUID id;
    private String code;
    private Integer discount;
    private Date valid;

    @ManyToOne
    @JoinColumn(name = "events_id")
    private Event event;
}
