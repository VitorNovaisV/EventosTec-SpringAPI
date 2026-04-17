package com.eventostec.api.domain.event;

import com.eventostec.api.domain.coupon.Coupon;

import java.util.Date;
import java.util.List;
import java.util.UUID;

//DTO used for receiving the data of events, with a list of coupons related linked to it

public record EventDetailsDTO(
        UUID id,

        String title,

        String description,

        Date date,

        String city,

        String state,

        Boolean remote,

        String imageUrl,

        String eventUrl,

        List<CouponDTO> coupons
    ){

    public record CouponDTO(

        String code,

        Integer discount,

        Date validUntil
    ){}
}

