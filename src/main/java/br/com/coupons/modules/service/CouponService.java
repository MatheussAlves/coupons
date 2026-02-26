package br.com.coupons.modules.service;

import br.com.coupons.modules.model.Coupon;
import br.com.coupons.modules.repository.CouponRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class CouponService {

    private final CouponRepository couponRepository;


    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }


    @Transactional
    public Coupon create(String code,
                         String description,
                         BigDecimal discountValue,
                         LocalDateTime expirationDate,
                         boolean published) {

        String codigoValido = code.replaceAll("[^a-zA-Z0-9]", "");

        if (couponRepository.existsByCodeAndDeletedFalse(codigoValido)) {
            throw new IllegalArgumentException("Esse codigo de cupom já existe, informe outro.");
        }

        Coupon coupon = new Coupon(
                code,
                description,
                discountValue,
                expirationDate,
                published
        );

        return couponRepository.save(coupon);
    }

    @Transactional
    public void delete(Long id) {

        Coupon coupon = couponRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new IllegalArgumentException("Cupom não encontrado. "));

        coupon.delete();

        couponRepository.save(coupon);
    }


    public Coupon findById(Long id) {
        return couponRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new IllegalArgumentException("Cupom não encontrado. "));
    }
}
