package com.naufalzaul.openmarketid.service.mapper;

import com.naufalzaul.openmarketid.entity.Product;
import com.naufalzaul.openmarketid.entity.TransactionDetail;
import com.naufalzaul.openmarketid.model.response.TransactionDetailResponse;
import com.naufalzaul.openmarketid.model.response.TransactionDetailTaxResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionDetailMapper {
    private final TransactionDetailTaxMapper detailTaxMapper;

    public TransactionDetailResponse fromTransactionDetail(TransactionDetail transactionDetail) {
        Product product = transactionDetail.getProduct();
        List<TransactionDetailTaxResponse> detailTaxResponses = transactionDetail
                .getTransactionDetailTaxes()
                .stream()
                .map(detailTaxMapper::fromTransactionDetailTax).toList();

        return new TransactionDetailResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                transactionDetail.getTotalQuantity(),
                transactionDetail.getTaxAmount(),
                detailTaxResponses
        );
    }
}
