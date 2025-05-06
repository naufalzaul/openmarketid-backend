package com.naufalzaul.openmarketid.service.mapper;

import com.naufalzaul.openmarketid.entity.ProductTax;
import com.naufalzaul.openmarketid.model.response.ProductTaxResponse;
import org.springframework.stereotype.Service;

@Service
public class ProductTaxMapper {
    public ProductTaxResponse fromProductTax(ProductTax productTax) {
        return new ProductTaxResponse(
                productTax.getId(),
                productTax.getProduct().getId(),
                productTax.getTax().getId(),
                productTax.getIsActive()
        );
    }
}
