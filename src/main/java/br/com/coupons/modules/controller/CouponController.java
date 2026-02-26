package br.com.coupons.modules.controller;

import br.com.coupons.modules.dto.CouponRequest;
import br.com.coupons.modules.dto.CouponResponse;
import br.com.coupons.modules.dto.CouponResponseError;
import br.com.coupons.modules.model.Coupon;
import br.com.coupons.modules.service.CouponService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coupons")
public class CouponController {
    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping
    public ResponseEntity create(
            @Valid @RequestBody CouponRequest request) {
        try {
            Coupon coupon = couponService.create(
                    request.getCode(),
                    request.getDescription(),
                    request.getDiscountValue(),
                    request.getExpirationDate(),
                    request.isPublished()
            );

            return ResponseEntity
                    .ok(new CouponResponse(coupon));
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
            return ResponseEntity
                    .badRequest().body(new CouponResponseError(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            couponService.delete(id);
            return ResponseEntity.ok().build();
        }catch(IllegalArgumentException e){
            return ResponseEntity
                    .badRequest().body(new CouponResponseError(e.getMessage()));        }

    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {

        try{
            Coupon coupon = couponService.findById(id);
            return ResponseEntity.ok(new CouponResponse(coupon));
        }catch(IllegalArgumentException e){
            return ResponseEntity
                    .badRequest().body(new CouponResponseError(e.getMessage()));
        }



    }
}
