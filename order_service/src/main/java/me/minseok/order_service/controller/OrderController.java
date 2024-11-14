package me.minseok.order_service.controller;

import java.util.List;
import me.minseok.order_service.dto.FinishOrderDto;
import me.minseok.order_service.dto.ProductOrderDetailDto;
import me.minseok.order_service.dto.StartOrderDto;
import me.minseok.order_service.dto.StartOrderResponseDto;
import me.minseok.order_service.entity.ProductOrder;
import me.minseok.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;


    @PostMapping("/order/start-order")
    public StartOrderResponseDto startOrder(@RequestBody StartOrderDto startOrderDto) {
        return orderService.startOrder(startOrderDto.getUserId(), startOrderDto.getProductId(), startOrderDto.getCount());
    }

    @PostMapping("/order/finish-order")
    public ProductOrder finishOrder(@RequestBody FinishOrderDto finishOrderDto) {
        return orderService.finishOrder(finishOrderDto.getOrderId(), finishOrderDto.getPaymentMethodId(), finishOrderDto.getAddressId());
    }

    @GetMapping("/order/users/{userId}/orders")
    public List<ProductOrder> getUserOrders(@PathVariable Long userId) {
        return orderService.getUserOrders(userId);
    }

    @GetMapping("/order/orders/{orderId}")
    public ProductOrderDetailDto getOrderDetail(@PathVariable Long orderId) {
        return orderService.getOrderDetail(orderId);
    }
}
