package me.minseok.order_service.service;

import com.google.protobuf.InvalidProtocolBufferException;
import java.util.Map;
import javax.xml.catalog.Catalog;
import kafka.protobuf.EdaMessage;
import kafka.protobuf.EdaMessage.DeliveryRequestV1;
import kafka.protobuf.EdaMessage.DeliveryStatusUpdateV1;
import kafka.protobuf.EdaMessage.PaymentResultV1;
import me.minseok.order_service.dto.DecreaseStockCountDto;
import me.minseok.order_service.entity.ProductOrder;
import me.minseok.order_service.enums.OrderStatus;
import me.minseok.order_service.feign.CatalogClient;
import me.minseok.order_service.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    KafkaTemplate<String, byte[]> kafkaTemplate;

    @Autowired
    CatalogClient catalogClient;

    @Autowired
    OrderRepository orderRepository;

    @KafkaListener(topics = "payment_result")
    public void consumePaymentResult(byte[] message) throws InvalidProtocolBufferException {
        PaymentResultV1 paymentResultV1 = PaymentResultV1.parseFrom(message);

        logger.info("[payment_result] consumed: {}", paymentResultV1);

        ProductOrder productOrder = orderRepository.findById(paymentResultV1.getOrderId()).orElseThrow();
        productOrder.setPaymentId(paymentResultV1.getPaymentId());
        productOrder.setOrderStatus(OrderStatus.DELIVERY_REQUESTED);
        orderRepository.save(productOrder);

        Map<String, Object> product = catalogClient.getProduct(productOrder.getProductId());

        DeliveryRequestV1 deliveryRequestMessage = DeliveryRequestV1.newBuilder()
                .setOrderId(productOrder.getId())
                .setProductName(product.get("name").toString())
                .setProductCount(productOrder.getCount())
                .setAddress(productOrder.getDeliveryAddress())
                .build();
        kafkaTemplate.send("delivery_request", deliveryRequestMessage.toByteArray());
    }

    @KafkaListener(topics = "delivery_status_update")
    public void consumeDeliveryStatusUpdate(byte[] message) throws InvalidProtocolBufferException {
        DeliveryStatusUpdateV1 deliveryStatusUpdateV1 = DeliveryStatusUpdateV1.parseFrom(message);

        logger.info("[delivery_status_update] consumed: {}", deliveryStatusUpdateV1);

        if (deliveryStatusUpdateV1.getDeliveryStatus().equals("REQUESTED")) {
            ProductOrder productOrder = orderRepository.findById(deliveryStatusUpdateV1.getOrderId()).orElseThrow();
            DecreaseStockCountDto decreaseStockCountDto = new DecreaseStockCountDto();
            decreaseStockCountDto.setDecreaseCount(productOrder.getCount());
            catalogClient.decreaseStockCount(productOrder.getProductId(), decreaseStockCountDto);
        }
    }
}
