package com.eventostec.api.service;

import com.eventostec.api.domain.coupon.Coupon;
import com.eventostec.api.domain.coupon.CouponRequestDTO;
import com.eventostec.api.domain.event.Event;
import com.eventostec.api.repositories.CouponRepository;
import com.eventostec.api.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private EventRepository eventRepository;

    //Auto importing the Service using the "Autowired" annotation

    //method that links a coupon to an event

    public Coupon addCouponToEvent(UUID eventId, CouponRequestDTO data) {

        //look to see if the desired eventId to link, actually exists
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event with id " + eventId + " not found."));

        Coupon newCoupon = new Coupon();
        //Creating a new coupon object

        newCoupon.setCode(data.code());
        newCoupon.setDiscount(data.discount());
        newCoupon.setValid(new Date(data.valid()));
        newCoupon.setEvent(event);
        //setting the attributes

        //saving in the repository
        return couponRepository.save(newCoupon);

    }

    //method that finds coupons that are valid, searching by the eventId

    public List<Coupon> consultCoupons(UUID eventId, Date currentDate) {

        //method call to the repository
        return couponRepository.findByEventIdAndValidAfter(eventId, currentDate);
    }

}
