package com.naufalzaul.openmarketid.service.mapper;

import com.naufalzaul.openmarketid.entity.Product;
import com.naufalzaul.openmarketid.entity.Transaction;
import com.naufalzaul.openmarketid.entity.TransactionDetail;
import com.naufalzaul.openmarketid.model.response.CustomerResponse;
import com.naufalzaul.openmarketid.model.response.TransactionDetailResponse;
import com.naufalzaul.openmarketid.model.response.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionDetailMapper {

    public TransactionDetailResponse fromTransactionDetail(TransactionDetail transactionDetail) {
        Product product = transactionDetail.getProduct();

        List<TransactionDetailResponse.DetailTaxResponse> taxResponses = getDetailTaxResponses(transactionDetail);

        return new TransactionDetailResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                transactionDetail.getTotalQuantity(),
                transactionDetail.getTaxAmount(),
                taxResponses
        );
    }

    private static List<TransactionDetailResponse.DetailTaxResponse> getDetailTaxResponses(TransactionDetail transactionDetail) {
        return transactionDetail.getTransactionDetailTaxes()
                .stream()
                .map(tax -> new TransactionDetailResponse.DetailTaxResponse(
                        tax.getTax().getId(),
                        tax.getTax().getDescription(),
                        tax.getTaxPercentage()
                ))
                .collect(Collectors.toList());
    }
}
