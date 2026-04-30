package com.eventostec.api.service;

import com.eventostec.api.domain.coupon.CouponRequestDTO;
import com.eventostec.api.repositories.CouponRepository;
import com.eventostec.api.repositories.EventRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    //creating a "fake" version of the repositories for tests
    // with @Mock and @inkectMocks

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private CouponService couponService;

    //@DisplayName would be a description to the test

    @Test
    @DisplayName("Deve soltar uma exceção quando o evento não existir")
    void shouldThrowExceptionWhenEventNotFound() {

        UUID eventId = UUID.randomUUID();
        CouponRequestDTO dto = new CouponRequestDTO("PROMO10", 10, 1745175600000L);

        //creating a dto and uuid

        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        //Simulating a case where the return is empty

        assertThrows(IllegalArgumentException.class, () -> {
            couponService.addCouponToEvent(eventId, dto);
        });

        // expected exception when sending that method
    }


}