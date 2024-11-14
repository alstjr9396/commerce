package me.minseok.order_service.feign;

import java.util.Map;
import me.minseok.order_service.dto.DecreaseStockCountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "catalogClient", url = "http://catalog-service:8080")
public interface CatalogClient {

    @GetMapping(value = "/catalog/products/{productId}")
    Map<String, Object> getProduct(@PathVariable Long productId);

    @PostMapping(value = "/catalog/products/{productId}/decreaseStockCount")
    void decreaseStockCount(@PathVariable Long productIdm, @RequestBody DecreaseStockCountDto decreaseStockCountDto);
}
