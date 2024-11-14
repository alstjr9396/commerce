package me.minseok.payment_service.controller;

import me.minseok.payment_service.dto.PaymentMethodDto;
import me.minseok.payment_service.dto.ProcessPaymentDto;
import me.minseok.payment_service.entity.Payment;
import me.minseok.payment_service.entity.PaymentMethod;
import me.minseok.payment_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("/payment/methods")
    public PaymentMethod registerPaymentMethod(@RequestBody PaymentMethodDto paymentMethodDto) {
        return paymentService.registerPaymentMethod(
                paymentMethodDto.getUserId(),
                paymentMethodDto.getPaymentMethodType(),
                paymentMethodDto.getCreditCardNumber()
        );
    }

    @PostMapping("/payment/process-payment")
    public Payment processPayment(@RequestBody ProcessPaymentDto processPaymentDto) throws Exception {
        return paymentService.processPayment(
                processPaymentDto.getUserId(),
                processPaymentDto.getOrderId(),
                processPaymentDto.getAmountKRW(),
                processPaymentDto.getPaymentMethodId()
        );
    }

    @GetMapping("/payment/users/{userId}/first-method")
    public PaymentMethod getPaymentMethod(@PathVariable Long userId) {
        return paymentService.getPaymentMethod(userId);
    }

    @GetMapping("/payment/payments/{paymentId}")
    public Payment getPayment(@PathVariable Long paymentId) {
        return paymentService.getPayment(paymentId);
    }

}
