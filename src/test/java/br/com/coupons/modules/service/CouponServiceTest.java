package br.com.coupons.modules.service;

import br.com.coupons.modules.model.Coupon;
import br.com.coupons.modules.repository.CouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class CouponServiceTest {
    @Mock
    private CouponRepository couponRepository;

    @InjectMocks
    private CouponService couponService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testaCriarCupom() {

        when(couponRepository.existsByCodeAndDeletedFalse("ABC123"))
                .thenReturn(false);

        when(couponRepository.save(any(Coupon.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Coupon coupon = couponService.create(
                "ABC123",
                "Black Friday",
                new BigDecimal("10.0"),
                LocalDateTime.now(),
                true
        );

        assertEquals("ABC123", coupon.getCode());
    }


    @Test
    void testaDeleteCupom() {

        Coupon coupon = new Coupon(
                "ABC123",
                "Test",
                new BigDecimal("10.0"),
                LocalDateTime.now(),
                false
        );

        when(couponRepository.findById(1L))
                .thenReturn(Optional.of(coupon));

        couponService.delete(1L);

        assertTrue(coupon.isDeleted());
    }
}
