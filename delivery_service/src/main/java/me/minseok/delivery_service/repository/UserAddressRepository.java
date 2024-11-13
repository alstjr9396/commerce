package me.minseok.delivery_service.repository;

import java.util.List;
import me.minseok.delivery_service.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {

    List<UserAddress> findByUserId(Long userId);

}
