package me.minseok.order_service.dto;

import java.util.Map;

public class StartOrderResponseDto {

    private Long orderId;

    private Map<String, Object> paymentMethod;

    private Map<String, Object> address;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Map<String, Object> getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Map<String, Object> paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Map<String, Object> getAddress() {
        return address;
    }

    public void setAddress(Map<String, Object> address) {
        this.address = address;
    }
}
