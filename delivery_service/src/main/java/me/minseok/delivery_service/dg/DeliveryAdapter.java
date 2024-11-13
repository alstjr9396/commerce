package me.minseok.delivery_service.dg;

public interface DeliveryAdapter {

    Long processDelivery(String productName, Long productCount, String address);

}
