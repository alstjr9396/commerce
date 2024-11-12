package me.minseok.payment_service.service;

import me.minseok.payment_service.entity.Payment;
import me.minseok.payment_service.entity.PaymentMethod;
import me.minseok.payment_service.enums.PaymentMethodType;
import me.minseok.payment_service.enums.PaymentStatus;
import me.minseok.payment_service.pg.CreditCardPaymentAdapter;
import me.minseok.payment_service.repostiroy.PaymentMethodRepository;
import me.minseok.payment_service.repostiroy.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    PaymentMethodRepository paymentMethodRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    CreditCardPaymentAdapter creditCardPaymentAdapter;

    public PaymentMethod registerPaymentMethod(Long userId, PaymentMethodType paymentMethodType, String creditCardNumber) {
        PaymentMethod paymentMethod = new PaymentMethod(userId, paymentMethodType, creditCardNumber);
        return paymentMethodRepository.save(paymentMethod);
    }

    public Payment processPayment(Long userId, Long orderId, Long amountKRW, Long paymentMethodId) throws Exception {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId).orElseThrow();
        if (!paymentMethod.getPaymentMethodType().equals(PaymentMethodType.CREDIT_CARD)) {
            throw new Exception("Unsupported payment method type");
        }

        Long referenceCode = creditCardPaymentAdapter.processCreditPayment(amountKRW, paymentMethod.getCreditCardNumber());
        Payment payment = new Payment(userId, orderId, amountKRW, paymentMethod.getPaymentMethodType(), paymentMethod.getCreditCardNumber()
                , PaymentStatus.COMPLETED, referenceCode);
        return paymentRepository.save(payment);
    }

    public PaymentMethod getPaymentMethod(Long userId) {
        return paymentMethodRepository.findByUserId(userId).stream().findFirst().orElseThrow();
    }

    public Payment getPayment(Long paymentId) {
        return paymentRepository.findById(paymentId).orElseThrow();
    }
}
