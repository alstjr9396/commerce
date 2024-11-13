package me.minseok.delivery_service.dg;

import java.util.List;
import me.minseok.delivery_service.entity.Delivery;
import me.minseok.delivery_service.enums.DeliveryStatus;
import me.minseok.delivery_service.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DeliveryStatusUpdater {

    @Autowired
    DeliveryRepository deliveryRepository;

    @Scheduled(fixedDelay = 10000)
    public void deliveryStatusUpdate() {
        System.out.println("---------- delivery status update ----------");

        List<Delivery> inDeliveryList = deliveryRepository.findAllByDeliveryStatus(DeliveryStatus.IN_DELIVERY);
        inDeliveryList.forEach(delivery -> {
            delivery.setDeliveryStatus(DeliveryStatus.COMPLETED);
            deliveryRepository.save(delivery);
        });

        List<Delivery> requestedList = deliveryRepository.findAllByDeliveryStatus(DeliveryStatus.REQUESTED);
        requestedList.forEach(delivery -> {
            delivery.setDeliveryStatus(DeliveryStatus.IN_DELIVERY);
            deliveryRepository.save(delivery);
        });
    }

}
