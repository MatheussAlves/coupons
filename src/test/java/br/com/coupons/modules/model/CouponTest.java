package br.com.coupons.modules.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CouponTest {

    @Test
    public void testaCriarCupom(){
        Coupon coupon = new Coupon(
                "ABC123",
                "Black Friday",
                new BigDecimal("10.0"),
                LocalDateTime.now(),
                true
        );
        assertEquals("ABC123", coupon.getCode());
        assertFalse(coupon.isDeleted());
    }

    @Test
    void validaCodigo() {
        Coupon coupon = new Coupon(
                "AB-C@12#3",
                "Teste",
                new BigDecimal("10.0"),
                LocalDateTime.now(),
                false
        );

        assertEquals("ABC123", coupon.getCode());
    }

    @Test
    void validaTamanhoCodigo() {
        assertThrows(IllegalArgumentException.class, () ->
                new Coupon(
                        "ABC12",
                        "Teste",
                        new BigDecimal("10.0"),
                        LocalDateTime.now(),
                        false
                )
        );
    }

    @Test
    void validaDescontoCupom() {
        assertThrows(IllegalArgumentException.class, () ->
                new Coupon(
                        "ABC123",
                        "Teste",
                        new BigDecimal("0.4"),
                        LocalDateTime.now(),
                        false
                )
        );
    }

    @Test
    void validaDataPassado() {
        assertThrows(IllegalArgumentException.class, () ->
                new Coupon(
                        "ABC123",
                        "Teste",
                        new BigDecimal("10.0"),
                        LocalDateTime.now().minusDays(1),
                        false
                )
        );
    }

    @Test
    void testaDelete() {
        Coupon coupon = new Coupon(
                "ABC123",
                "Teste",
                new BigDecimal("10.0"),
                LocalDateTime.now(),
                false
        );

        coupon.delete();

        assertTrue(coupon.isDeleted());
    }

    @Test
    void testaCupomJaDeletado() {
        Coupon coupon = new Coupon(
                "ABC123",
                "Test",
                new BigDecimal("10.0"),
                LocalDateTime.now(),
                false
        );

        coupon.delete();

        assertThrows(IllegalStateException.class, coupon::delete);
    }
}
