package me.minseok.payment_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import me.minseok.payment_service.enums.PaymentMethodType;

@Entity
@Table(indexes = {@Index(name = "idx_userId", columnList = "userId")})
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private PaymentMethodType paymentMethodType;

    private String creditCardNumber;

    public PaymentMethod() {
    }

    public PaymentMethod(Long userId, PaymentMethodType paymentMethodType, String creditCardNumber) {
        this.userId = userId;
        this.paymentMethodType = paymentMethodType;
        this.creditCardNumber = creditCardNumber;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public PaymentMethodType getPaymentMethodType() {
        return paymentMethodType;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }
}
