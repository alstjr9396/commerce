package me.minseok.payment_service.dto;

public class ProcessPaymentDto {

    private Long userId;

    private Long orderId;

    private Long amountKRW;

    private Long paymentMethodId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getAmountKRW() {
        return amountKRW;
    }

    public void setAmountKRW(Long amountKRW) {
        this.amountKRW = amountKRW;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
}
