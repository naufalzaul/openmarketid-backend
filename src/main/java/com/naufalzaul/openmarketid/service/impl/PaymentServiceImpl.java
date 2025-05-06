package com.naufalzaul.openmarketid.service.impl;

import com.naufalzaul.openmarketid.model.request.payment.PaymentRequest;
import com.naufalzaul.openmarketid.model.response.TransactionResponse;
import com.naufalzaul.openmarketid.repository.TransactionRepository;
import com.naufalzaul.openmarketid.service.PaymentService;
import com.naufalzaul.openmarketid.service.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Transactional
    @Override
    public TransactionResponse payment(PaymentRequest paymentRequest) {
//        Transaction findTransaction = transactionRepository
//                .findById(paymentRequest.getTransactionId())
//                .orElseThrow(() -> new DataNotFoundException(
//                        format("Transaction not found with id %s",
//                               paymentRequest.getTransactionId())));
//
//        if (findTransaction.getTotalAmount() > paymentRequest.getTotalPayment()){
//            throw new RuntimeException("Amount of money less than the total payment");
//        }
//
//        findTransaction.setPaymentStatus(PaymentStatus.PAID);
//        findTransaction.setPaymentDate(LocalDateTime.now());
//
//        Transaction transaction = transactionRepository.save(findTransaction);
//        return transactionMapper.fromTransaction(transaction);
        return null;
    }
}
