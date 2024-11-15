package me.minseok.payment_service.service;

import com.google.protobuf.InvalidProtocolBufferException;
import kafka.protobuf.EdaMessage;
import kafka.protobuf.EdaMessage.PaymentRequestV1;
import kafka.protobuf.EdaMessage.PaymentResultV1;
import me.minseok.payment_service.entity.Payment;
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
    PaymentService paymentService;

    @KafkaListener(topics = "payment_request")
    public void consumePaymentRequest(byte[] message) throws Exception {
        PaymentRequestV1 paymentRequestV1 = PaymentRequestV1.parseFrom(message);

        logger.info("[payment_request] consumed: {}", paymentRequestV1);

        Payment payment = paymentService.processPayment(
                paymentRequestV1.getUserId(),
                paymentRequestV1.getOrderId(),
                paymentRequestV1.getAmountKRW(),
                paymentRequestV1.getPaymentMethodId()
        );

        PaymentResultV1 paymentResultMessage = PaymentResultV1.newBuilder()
                .setOrderId(payment.getOrderId())
                .setPaymentId(payment.getId())
                .setPaymentStatus(payment.getPaymentStatus().toString())
                .build();

        kafkaTemplate.send("payment_result", paymentResultMessage.toByteArray());
    }
}
