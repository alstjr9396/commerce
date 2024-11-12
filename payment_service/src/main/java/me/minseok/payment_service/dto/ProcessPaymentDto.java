package me.minseok.payment_service.dto;

public class ProcessPaymentDto {

    private Long userId;

    private Long orderId;

    private Long amountKRW;

    private Long paymentId;

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

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }
}
