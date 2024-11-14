package me.minseok.catalog_service.mysql.repository;

import java.util.List;
import me.minseok.catalog_service.mysql.entity.SellerProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerProductRepository extends JpaRepository<SellerProduct, Long> {

    List<SellerProduct> findBySellerId(Long sellerId);
}
