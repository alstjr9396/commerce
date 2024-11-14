package me.minseok.order_service.repository;

import java.util.List;
import me.minseok.order_service.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<ProductOrder, Long> {

    List<ProductOrder> findByUserId(Long userId);
}
