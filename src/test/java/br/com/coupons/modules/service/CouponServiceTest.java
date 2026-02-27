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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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
                LocalDateTime.now().plusDays(1),
                true
        );

        assertEquals("ABC123", coupon.getCode());
    }

    @Test
    void testaCupomComCOdigoExistente() {

        when(couponRepository.existsByCodeAndDeletedFalse("ABC123"))
                .thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
                couponService.create(
                        "ABC123",
                        "Test",
                        new BigDecimal("10.0"),
                        LocalDateTime.now().plusDays(1),
                        false
                )
        );

        verify(couponRepository, never()).save(any());
    }

    @Test
    void testaDeleteCupom() {

        Coupon coupon = new Coupon(
                "ABC123",
                "Test",
                new BigDecimal("10.0"),
                LocalDateTime.now().plusDays(1),
                false
        );

        when(couponRepository.findByIdAndDeletedFalse(1L))
                .thenReturn(Optional.of(coupon));

        couponService.delete(1L);

        assertTrue(coupon.isDeleted());
    }

    @Test
    void testaCupomNaoEncontrado() {

        when(couponRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                couponService.delete(1L)
        );
    }

    @Test
    void testaCupomJaDeletado() {

        Coupon coupon = new Coupon(
                "ABC123",
                "Test",
                new BigDecimal("10.0"),
                LocalDateTime.now().plusDays(1),
                false
        );

        coupon.delete(); // already deleted

        when(couponRepository.findById(1L))
                .thenReturn(Optional.of(coupon));

        assertThrows(IllegalStateException.class, () ->
                couponService.delete(1L)
        );
    }
}
