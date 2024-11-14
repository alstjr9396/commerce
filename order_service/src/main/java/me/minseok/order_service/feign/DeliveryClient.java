package me.minseok.order_service.feign;

import java.util.Map;
import me.minseok.order_service.dto.ProcessDeliveryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "deliveryClient", url = "http://delivery-service:8080")
public interface DeliveryClient {

    @GetMapping(value = "/delivery/users/{userId}/first-address")
    Map<String, Object> getUserAddress(@PathVariable Long userId);

    @GetMapping(value = "/delivery/address/{addressId}")
    Map<String, Object> getAddress(@PathVariable Long addressId);

    @GetMapping(value = "/delivery/process-delivery")
    Map<String, Object> processDelivery(@RequestBody ProcessDeliveryDto processDeliveryDto);

    @GetMapping(value = "/delivery/deliveries/{deliveryId}")
    Map<String, Object> getDelivery(@PathVariable Long deliveryId);
}
