package me.minseok.order_service.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import me.minseok.order_service.dto.DecreaseStockCountDto;
import me.minseok.order_service.dto.FinishOrderDto;
import me.minseok.order_service.dto.ProcessDeliveryDto;
import me.minseok.order_service.dto.ProcessPaymentDto;
import me.minseok.order_service.dto.ProductOrderDetailDto;
import me.minseok.order_service.dto.StartOrderDto;
import me.minseok.order_service.dto.StartOrderResponseDto;
import me.minseok.order_service.entity.ProductOrder;
import me.minseok.order_service.enums.OrderStatus;
import me.minseok.order_service.feign.CatalogClient;
import me.minseok.order_service.feign.DeliveryClient;
import me.minseok.order_service.feign.PaymentClient;
import me.minseok.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    PaymentClient paymentClient;

    @Autowired
    DeliveryClient deliveryClient;

    @Autowired
    CatalogClient catalogClient;

    public StartOrderResponseDto startOrder(Long userId, Long productId, Long count) {
        Map<String, Object> product = catalogClient.getProduct(productId);
        Map<String, Object> paymentMethod = paymentClient.getPaymentMethod(userId);
        Map<String, Object> userAddress = deliveryClient.getUserAddress(userId);

        ProductOrder productOrder = new ProductOrder(userId, productId, count, OrderStatus.INITIATED, null, null);
        orderRepository.save(productOrder);

        StartOrderResponseDto startOrderResponseDto = new StartOrderResponseDto();
        startOrderResponseDto.setOrderId(productOrder.getId());
        startOrderResponseDto.setPaymentMethod(paymentMethod);
        startOrderResponseDto.setAddress(userAddress);

        return startOrderResponseDto;
    }

    public ProductOrder finishOrder(Long orderId, Long paymentMethodId, Long addressId) {
        ProductOrder order = orderRepository.findById(orderId).orElseThrow();
        Map<String, Object> product = catalogClient.getProduct(order.getProductId());

        ProcessPaymentDto processPaymentDto = new ProcessPaymentDto();
        processPaymentDto.setOrderId(order.getId());
        processPaymentDto.setUserId(order.getUserId());
        processPaymentDto.setAmountKRW(Long.parseLong(product.get("price").toString()) * order.getCount());
        processPaymentDto.setPaymentMethodId(paymentMethodId);
        Map<String, Object> payment = paymentClient.processPayment(processPaymentDto);

        Map<String, Object> address = deliveryClient.getAddress(addressId);
        ProcessDeliveryDto processDeliveryDto = new ProcessDeliveryDto();
        processDeliveryDto.setOrderId(order.getId());
        processDeliveryDto.setProductName(product.get("name").toString());
        processDeliveryDto.setProductCount(order.getCount());
        processDeliveryDto.setAddress(address.get("address").toString());
        Map<String, Object> delivery = deliveryClient.processDelivery(processDeliveryDto);

        DecreaseStockCountDto decreaseStockCountDto = new DecreaseStockCountDto();
        decreaseStockCountDto.setDecreaseCount(order.getCount());
        catalogClient.decreaseStockCount(order.getProductId(), decreaseStockCountDto);

        order.setPaymentId(Long.parseLong(payment.get("id").toString()));
        order.setDeliveryId(Long.parseLong(delivery.get("id").toString()));
        order.setOrderStatus(OrderStatus.DELIVERY_REQUESTED);

        return orderRepository.save(order);
    }

    public List<ProductOrder> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public ProductOrderDetailDto getOrderDetail(Long orderId) {
        ProductOrder order = orderRepository.findById(orderId).orElseThrow();

        Map<String, Object> payment = paymentClient.getPayment(order.getPaymentId());
        Map<String, Object> delivery = deliveryClient.getDelivery(order.getDeliveryId());

        ProductOrderDetailDto productOrderDetailDto = new ProductOrderDetailDto();
        productOrderDetailDto.setId(order.getId());
        productOrderDetailDto.setUserId(order.getUserId());
        productOrderDetailDto.setProductId(order.getProductId());
        productOrderDetailDto.setPaymentId(order.getPaymentId());
        productOrderDetailDto.setDeliveryId(order.getDeliveryId());
        productOrderDetailDto.setOrderStatus(order.getOrderStatus());
        productOrderDetailDto.setPaymentStatus(payment.get("paymentStatus").toString());
        productOrderDetailDto.setDeliveryStatus(delivery.get("status").toString());

        return productOrderDetailDto;
    }

}
