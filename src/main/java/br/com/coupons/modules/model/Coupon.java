package br.com.coupons.modules.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "coupon")
@NoArgsConstructor
public class Coupon {

    private static final int CODE_LENGTH = 6;
    private static final BigDecimal MIN_DISCOUNT = new BigDecimal("0.5");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(nullable = false, unique = true, length = 6)
    @Getter
    private String code;

    @Column(nullable = false)
    @Getter
    private String description;

    @Column(nullable = false)
    @Getter
    private BigDecimal discountValue;

    @Column(nullable = false)
    @Getter
    private LocalDateTime expirationDate;

    @Column(nullable = false)
    @Getter
    private boolean published;

    @Column(nullable = false)
    @Getter
    private boolean deleted = false;

    @Column(nullable = false)
    @Getter
    private boolean redeemed;



    public Coupon(String code,
                  String description,
                  BigDecimal discountValue,
                  LocalDateTime expirationDate,
                  boolean published) {

        this.code = validateCode(code);
        this.description = Objects.requireNonNull(description, "Informe uma descrição");
        this.discountValue = validateDiscount(discountValue);
        this.expirationDate = validateExpirationDate(expirationDate);
        this.published = published;
        this.redeemed = false;
    }


    private String validateCode(String code) {
        if (code == null) {
            throw new IllegalArgumentException("O codigo é obrigatorio");
        }

        String cupomValido = code.replaceAll("[^a-zA-Z0-9]", "");

        if (cupomValido.length() != CODE_LENGTH) {
            throw new IllegalArgumentException("O codigo deve ter exatos 6 caracteres alfanumericos !");
        }

        return cupomValido;
    }

    private BigDecimal validateDiscount(BigDecimal discountValue) {
        if (discountValue == null || discountValue.compareTo(MIN_DISCOUNT) < 0) {
            throw new IllegalArgumentException("O disconto deve ser no minimo 0.5 !");
        }
        return discountValue;
    }

    private LocalDateTime validateExpirationDate(LocalDateTime expirationDate) {
        if (expirationDate == null || expirationDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("A data de expiração não pode ser no passado! ");
        }
        return expirationDate;
    }

    public void delete() {
        if (this.deleted) {
            throw new IllegalStateException("Coupon já deletado!");
        }
        this.deleted = true;
    }
}