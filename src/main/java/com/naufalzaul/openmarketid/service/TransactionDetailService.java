package com.naufalzaul.openmarketid.service;

import com.naufalzaul.openmarketid.entity.Transaction;
import com.naufalzaul.openmarketid.entity.TransactionDetail;
import com.naufalzaul.openmarketid.model.request.transaction.TransactionDetailRequest;

import java.util.List;

public interface TransactionDetailService {
    TransactionDetail createTransactionDetail(
            TransactionDetailRequest request, Transaction transaction
    );
    List<TransactionDetail> findAllTransactionDetailsByTransactionId();
}
