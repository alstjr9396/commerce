package me.minseok.delivery_service.dg;


import org.springframework.stereotype.Component;

@Component
public class FastDeliveryAdapter implements DeliveryAdapter {

    @Override
    public Long processDelivery(String productName, Long productCount, String address) {
        return Math.round(Math.random() * 100000000);
    }
}
