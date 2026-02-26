package br.com.coupons.modules.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CouponRequest {
    @NotBlank
    @Getter
    private String code;

    @Getter
    @NotBlank
    private String description;

    @Getter
    @NotNull
    private BigDecimal discountValue;

    @Getter
    @NotNull
    private LocalDateTime expirationDate;

    @Getter
    private boolean published;


}
