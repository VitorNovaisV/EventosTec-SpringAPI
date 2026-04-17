package com.eventostec.api.domain.coupon;

//DTO used for receiving the data of coupons

public record CouponRequestDTO(String code, Integer discount, Long valid) {
}
