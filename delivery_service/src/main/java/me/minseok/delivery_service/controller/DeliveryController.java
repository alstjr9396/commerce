package me.minseok.delivery_service.controller;

import me.minseok.delivery_service.dto.ProcessDeliveryDto;
import me.minseok.delivery_service.dto.RegisterAddressDto;
import me.minseok.delivery_service.entity.Delivery;
import me.minseok.delivery_service.entity.UserAddress;
import me.minseok.delivery_service.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeliveryController {

    @Autowired
    DeliveryService deliveryService;

    @PostMapping("/delivery/addresses")
    public UserAddress registerAddress(@RequestBody RegisterAddressDto registerAddressDto) {
        return deliveryService.addUserAddress(registerAddressDto.getUserId(), registerAddressDto.getAddress(), registerAddressDto.getAlias());
    }

    @PostMapping("/delivery/process-delivery")
    public Delivery processDelivery(@RequestBody ProcessDeliveryDto processDeliveryDto) {
        return deliveryService.processDelivery(processDeliveryDto.getOrderId(), processDeliveryDto.getProductName(),
                processDeliveryDto.getProductCount(), processDeliveryDto.getAddress());
    }

    @GetMapping("/delivery/deliveries/{deliveryId}")
    public Delivery getDelivery(@PathVariable Long deliveryId) {
        return deliveryService.getDelivery(deliveryId);
    }

    @GetMapping("/delivery/address/{addressId}")
    public UserAddress getAddress(@PathVariable Long addressId) {
        return deliveryService.getAddress(addressId);
    }

    @GetMapping("/delivery/users/{userId}/first-address")
    public UserAddress getUserAddress(@PathVariable Long userId) {
        return deliveryService.getUserAddress(userId);
    }
}
