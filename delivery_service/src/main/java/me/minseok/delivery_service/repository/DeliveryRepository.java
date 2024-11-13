package me.minseok.delivery_service.repository;

import java.util.List;
import me.minseok.delivery_service.entity.Delivery;
import me.minseok.delivery_service.enums.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    List<Delivery> findByOrderId(Long orderId);

    List<Delivery> findAllByDeliveryStatus(DeliveryStatus deliveryStatus);
}
