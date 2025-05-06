package com.naufalzaul.openmarketid.service.impl;

import com.naufalzaul.openmarketid.entity.*;
import com.naufalzaul.openmarketid.model.request.transaction.TransactionDetailRequest;
import com.naufalzaul.openmarketid.repository.TransactionDetailRepository;
import com.naufalzaul.openmarketid.repository.TransactionDetailTaxRepository;
import com.naufalzaul.openmarketid.service.ProductService;
import com.naufalzaul.openmarketid.service.TransactionDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionDetailServiceImpl implements TransactionDetailService {

    private final ProductService productService;
    private final TransactionDetailRepository transactionDetailRepository;
    private final TransactionDetailTaxRepository transactionDetailTaxRepository;


    @Override
    public TransactionDetail createTransactionDetail(
            TransactionDetailRequest request, Transaction transaction
    ) {

        List<TransactionDetailTax> transactionDetailTaxes = new ArrayList<>();

        Product productById = productService.findProductById(request.getProductId());

        Double taxAmount = 0.0;
        Double netAmountPerProduct = request.getQuantity() * productById.getPrice();

        TransactionDetail createTransactionDetail = TransactionDetail.builder()
                .product(productById)
                .totalQuantity(request.getQuantity())
                .taxAmount(taxAmount)
                .transaction(transaction)
                .transactionDetailTaxes(transactionDetailTaxes)
                .build();

        TransactionDetail transactionDetail = transactionDetailRepository.save(createTransactionDetail);

        for (ProductTax productTax : productById.getProductTaxes()) {
            Double taxPercentage = productTax.getTax().getTaxPercentage();

            taxAmount += (netAmountPerProduct * (taxPercentage / 100));

            TransactionDetailTax transactionDetailTax = TransactionDetailTax.builder()
                    .tax(productTax.getTax())
                    .taxPercentage(taxPercentage)
                    .transactionDetail(transactionDetail)
                    .build();

            transactionDetail.getTransactionDetailTaxes().add(transactionDetailTax);
            transactionDetailTaxRepository.save(transactionDetailTax);
        }

        transactionDetail.setTaxAmount(taxAmount);

        return transactionDetailRepository.save(transactionDetail);
    }

    @Override
    public List<TransactionDetail> findAllTransactionDetailsByTransactionId() {
        return List.of();
    }
}
