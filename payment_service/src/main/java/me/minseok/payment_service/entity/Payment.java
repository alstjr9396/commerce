package me.minseok.payment_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import me.minseok.payment_service.enums.PaymentMethodType;
import me.minseok.payment_service.enums.PaymentStatus;

@Entity
@Table(indexes = {@Index(name = "idx_userId", columnList = "userId")})
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Column(unique = true)
    private Long orderId;

    private Long amountKRW;

    private PaymentMethodType paymentMethodType;

    private String paymentData;

    private PaymentStatus paymentStatus;

    @Column(unique = true)
    private Long referenceCode;

    public Payment() {
    }

    public Payment(Long userId, Long orderId, Long amountKRW, PaymentMethodType paymentMethodType, String paymentData,
            PaymentStatus paymentStatus, Long referenceCode) {
        this.userId = userId;
        this.orderId = orderId;
        this.amountKRW = amountKRW;
        this.paymentMethodType = paymentMethodType;
        this.paymentData = paymentData;
        this.paymentStatus = paymentStatus;
        this.referenceCode = referenceCode;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getAmountKRW() {
        return amountKRW;
    }

    public PaymentMethodType getPaymentMethodType() {
        return paymentMethodType;
    }

    public String getPaymentData() {
        return paymentData;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public Long getReferenceCode() {
        return referenceCode;
    }
}
