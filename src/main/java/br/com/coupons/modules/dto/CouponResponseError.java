package br.com.coupons.modules.dto;

import lombok.Getter;

public class CouponResponseError {
    @Getter
    String message;

    public CouponResponseError(String msg){
        this.message = msg;
    }
}
