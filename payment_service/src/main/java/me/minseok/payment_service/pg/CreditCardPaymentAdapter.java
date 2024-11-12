package me.minseok.payment_service.pg;

public interface CreditCardPaymentAdapter {

    Long processCreditPayment(Long amountKRW, String creditCardNumber);

}
