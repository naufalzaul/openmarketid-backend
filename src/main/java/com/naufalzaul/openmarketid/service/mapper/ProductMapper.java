package com.naufalzaul.openmarketid.service.mapper;

import com.naufalzaul.openmarketid.entity.Product;
import com.naufalzaul.openmarketid.model.response.ProductResponse;
import com.naufalzaul.openmarketid.model.response.ProductTaxResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductMapper {

    private final ProductTaxMapper productTaxMapper;
    private final TaxMapper taxMapper;

    public ProductResponse fromProduct(Product product) {
        List<ProductTaxResponse> productTaxResponses = product.getProductTaxes().stream()
                .map(productTaxMapper::fromProductTax).toList();

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getAvailableQuantity(),
                productTaxResponses
        );
    }
}
