package com.naufalzaul.openmarketid.service.mapper;

import com.naufalzaul.openmarketid.entity.Transaction;
import com.naufalzaul.openmarketid.model.response.CustomerResponse;
import com.naufalzaul.openmarketid.model.response.TransactionDetailResponse;
import com.naufalzaul.openmarketid.model.response.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionMapper {

    private final CustomerMapper customerMapper;
    private final TransactionDetailMapper detailMapper;

    public TransactionResponse fromTransaction(Transaction transaction) {

        CustomerResponse customerResponse = customerMapper.fromCustomer(transaction.getCustomer());

        List<TransactionDetailResponse> detailResponses =
                transaction.getTransactionDetails().stream().map(detailMapper::fromTransactionDetail).toList();

        return new TransactionResponse(
                transaction.getId(),
                customerResponse,
                transaction.getNetAmount(),
                transaction.getTotalTax(),
                transaction.getTotalAmount(),
                transaction.getTransactionDate(),
                transaction.getTransactionStatus(),
                detailResponses,
                transaction.getPaymentMethod(),
                transaction.getPaymentDate()
        );
    }
}
