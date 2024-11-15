package me.minseok.delivery_service.service;

import kafka.protobuf.EdaMessage;
import kafka.protobuf.EdaMessage.DeliveryRequestV1;
import kafka.protobuf.EdaMessage.DeliveryStatusUpdateV1;
import kafka.protobuf.EdaMessage.PaymentRequestV1;
import kafka.protobuf.EdaMessage.PaymentResultV1;
import me.minseok.delivery_service.entity.Delivery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventLisenter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    KafkaTemplate<String, byte[]> kafkaTemplate;

    @Autowired
    DeliveryService deliveryService;

    @KafkaListener(topics = "delivery_request")
    public void consumeDeliveryRequest(byte[] message) throws Exception {
        DeliveryRequestV1 deliveryRequestV1 = DeliveryRequestV1.parseFrom(message);

        logger.info("[delivery_request] consumed: {}", deliveryRequestV1);

        Delivery delivery = deliveryService.processDelivery(
                deliveryRequestV1.getOrderId(),
                deliveryRequestV1.getProductName(),
                deliveryRequestV1.getProductCount(),
                deliveryRequestV1.getAddress()
        );

        DeliveryStatusUpdateV1 deliveryStatusMessage = EdaMessage.DeliveryStatusUpdateV1.newBuilder()
                .setOrderId(delivery.getOrderId())
                .setDeliveryId(delivery.getId())
                .setDeliveryStatus(delivery.getDeliveryStatus().toString())
                .build();

        kafkaTemplate.send("delivery_status_update", deliveryStatusMessage.toByteArray());
    }
}
