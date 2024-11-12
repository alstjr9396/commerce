package me.minseok.payment_service.repostiroy;

import java.util.List;
import me.minseok.payment_service.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

    List<PaymentMethod> findByUserId(Long userId);

}
