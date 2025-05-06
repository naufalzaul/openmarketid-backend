package com.naufalzaul.openmarketid.service;

import com.naufalzaul.openmarketid.model.request.payment.PaymentRequest;
import com.naufalzaul.openmarketid.model.response.TransactionResponse;

public interface PaymentService {
    TransactionResponse payment(PaymentRequest paymentRequest);
}
