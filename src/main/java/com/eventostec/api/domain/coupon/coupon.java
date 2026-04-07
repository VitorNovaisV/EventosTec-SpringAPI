package com.eventostec.api.domain.coupon;

import com.eventostec.api.domain.event.Event;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "coupon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class coupon {

    @Id
    @GeneratedValue
    private UUID id;
    private Integer discount;
    private Date valid;

    @ManyToOne
    @JoinColumn(name = "events_id")
    private Event event;
}
