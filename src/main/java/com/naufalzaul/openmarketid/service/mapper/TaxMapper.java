package com.naufalzaul.openmarketid.service.mapper;

import com.naufalzaul.openmarketid.entity.Tax;
import com.naufalzaul.openmarketid.model.response.ProductTaxResponse;
import com.naufalzaul.openmarketid.model.response.TaxResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaxMapper {
    private final ProductTaxMapper productTaxMapper;

    public TaxResponse fromTax(Tax tax) {
        List<ProductTaxResponse> productTaxResponses = tax.getProductTaxes().stream()
                .map(productTaxMapper::fromProductTax).toList();

        return new TaxResponse(
                tax.getId(),
                tax.getDescription(),
                tax.getTaxPercentage(),
                tax.getIsActive(),
                productTaxResponses
        );
    }
}
