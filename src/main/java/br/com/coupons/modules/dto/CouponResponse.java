package br.com.coupons.modules.dto;

import br.com.coupons.modules.model.Coupon;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class CouponResponse {

    @Getter
    private Long id;
    @Getter
    private String code;
    @Getter
    private String description;
    @Getter
    private BigDecimal discountValue;
    @Getter
    private LocalDateTime expirationDate;
    @Getter
    private boolean published;

    public CouponResponse(Coupon coupon) {
        this.id = coupon.getId();
        this.code = coupon.getCode();
        this.description = coupon.getDescription();
        this.discountValue = coupon.getDiscountValue();
        this.expirationDate = coupon.getExpirationDate();
        this.published = coupon.isPublished();
    }
}
