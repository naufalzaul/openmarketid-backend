package com.naufalzaul.openmarketid.service.mapper;

import com.naufalzaul.openmarketid.entity.Product;
import com.naufalzaul.openmarketid.entity.Transaction;
import com.naufalzaul.openmarketid.model.response.CustomerResponse;
import com.naufalzaul.openmarketid.model.response.TransactionDetailResponse;
import com.naufalzaul.openmarketid.model.response.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class TransactionMapper {

    private final CustomerMapper customerMapper;
    private final TransactionDetailMapper transactionDetailMapper;

    public TransactionResponse fromTransaction(Transaction transaction) {

        CustomerResponse customerResponse = customerMapper.fromCustomer(transaction.getCustomer());

        List<TransactionDetailResponse> transactionDetailResponses = getTransactionDetailResponses(transaction);

        return new TransactionResponse(
                transaction.getId(),
                customerResponse,
                transaction.getNetAmount(),
                transaction.getTotalTax(),
                transaction.getTotalAmount(),
                transaction.getTransactionDate(),
                transaction.getTransactionStatus(),
                transactionDetailResponses,
                transaction.getPaymentMethod(),
                transaction.getPaymentDate()
        );
    }

    private List<TransactionDetailResponse> getTransactionDetailResponses(Transaction transaction) {
        return transaction.getTransactionDetails()
                .stream()
                .map(transactionDetailMapper::fromTransactionDetail)
                .collect(Collectors.toList());
    }
}
