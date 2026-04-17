package com.eventostec.api.controller;

import com.eventostec.api.domain.coupon.Coupon;
import com.eventostec.api.domain.coupon.CouponRequestDTO;
import com.eventostec.api.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

//Annotations to mark this class as a controller
//"RequestMapping sets the subdomain used"

@RestController
@RequestMapping("/api/coupon")
public class CouponController {

//Auto importing the Service using the "Autowired" annotation

    @Autowired
    private CouponService couponService;

    //The next method returns all the coupons found based on the eventId they are linked to

    //Setting the subdomain to be used for this method + defining it as a POST request
    //The following method receives as parameters a UUID from the url, and the data of the coupon from the body

    @PostMapping("/event/{eventId}")
    public ResponseEntity<Coupon> addCouponsToEvent(@PathVariable UUID eventId, @RequestBody CouponRequestDTO data) {

        //Calls the method from the Service
        Coupon coupons = couponService.addCouponToEvent(eventId, data);

        //Returns an http code + the contents of the previous method call.
        return ResponseEntity.ok(coupons);
    }


}
