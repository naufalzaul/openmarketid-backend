package com.naufalzaul.openmarketid.service;

import com.naufalzaul.openmarketid.entity.Transaction;
import com.naufalzaul.openmarketid.model.request.transaction.TransactionFilterRequest;
import com.naufalzaul.openmarketid.model.request.transaction.TransactionRequest;
import com.naufalzaul.openmarketid.model.response.TransactionResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest request);
    List<TransactionResponse> findAllTransactions();
    List<TransactionResponse> findAllTransactionsByUserEmail();
    Page<TransactionResponse> filterTransaction(TransactionFilterRequest request);
    void canceledTransaction(String id);
}
