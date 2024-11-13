package me.minseok.delivery_service.service;

import me.minseok.delivery_service.dg.DeliveryAdapter;
import me.minseok.delivery_service.entity.Delivery;
import me.minseok.delivery_service.entity.UserAddress;
import me.minseok.delivery_service.enums.DeliveryStatus;
import me.minseok.delivery_service.repository.DeliveryRepository;
import me.minseok.delivery_service.repository.UserAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    @Autowired
    UserAddressRepository userAddressRepository;

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    DeliveryAdapter deliveryAdapter;

    public UserAddress addUserAddress(Long userId, String address, String alias) {
        UserAddress userAddress = new UserAddress(userId, address, alias);
        return userAddressRepository.save(userAddress);
    }

    public Delivery processDelivery(Long orderId, String productName, Long productCount, String address) {
        Long referenceCode = deliveryAdapter.processDelivery(productName, productCount, address);
        Delivery delivery = new Delivery(orderId, productName, productCount, address, referenceCode, DeliveryStatus.REQUESTED);
        return deliveryRepository.save(delivery);
    }

    public Delivery getDelivery(Long deliveryId) {
        return deliveryRepository.findById(deliveryId).orElseThrow();
    }

    public UserAddress getAddress(Long addressId) {
        return userAddressRepository.findById(addressId).orElseThrow();
    }

    public UserAddress getUserAddress(Long userId) {
        return userAddressRepository.findByUserId(userId).stream().findFirst().orElseThrow();
    }
}
