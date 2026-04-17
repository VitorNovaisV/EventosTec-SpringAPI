package com.eventostec.api.repositories;

import com.eventostec.api.domain.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

//The Repository is the representation of the connection with the database, it has similar SQL like query's
//here it needs to extend the JpaRepository that already uses the variables to connect defined in the .dev

public interface CouponRepository  extends JpaRepository<Coupon, UUID> {

   //method that finds coupons that are valid, searching by the eventId
   //the Query used in this method is automatically created by context using the name, by spring

   List<Coupon> findByEventIdAndValidAfter(UUID eventId, Date currentDate);
}
