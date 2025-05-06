package com.naufalzaul.openmarketid.service.mapper;

import com.naufalzaul.openmarketid.entity.Tax;
import com.naufalzaul.openmarketid.entity.TransactionDetailTax;
import com.naufalzaul.openmarketid.model.response.TransactionDetailTaxResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionDetailTaxMapper {

    public TransactionDetailTaxResponse fromTransactionDetailTax(
            TransactionDetailTax transactionDetailTax) {
        Tax tax = transactionDetailTax.getTax();
        return new TransactionDetailTaxResponse(
                tax.getId(),
                tax.getDescription(),
                transactionDetailTax.getTaxPercentage()
        );
    }
}
