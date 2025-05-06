package com.naufalzaul.openmarketid.service.impl;

import com.naufalzaul.openmarketid.entity.Product;
import com.naufalzaul.openmarketid.entity.ProductTax;
import com.naufalzaul.openmarketid.entity.Tax;
import com.naufalzaul.openmarketid.exception.DataNotFoundException;
import com.naufalzaul.openmarketid.repository.ProductRepository;
import com.naufalzaul.openmarketid.repository.ProductTaxRepository;
import com.naufalzaul.openmarketid.repository.TaxRepository;
import com.naufalzaul.openmarketid.service.ProductTaxService;
import com.naufalzaul.openmarketid.service.mapper.ProductTaxMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductTaxServiceImpl implements ProductTaxService {

    private final ProductRepository productRepository;
    private final TaxRepository taxRepository;
    private final ProductTaxRepository productTaxRepository;
    private final ProductTaxMapper productTaxMapper;

    @Override
    public ProductTax findProductTaxById(String id) {
        return productTaxRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(
                        String.format("Product Tax not found with id %s", id)
                ));
    }

    @Override
    public void deleteProductTax(String id) {
        ProductTax productTaxById = findProductTaxById(id);
        productTaxRepository.deleteById(id);

        Product product = productTaxById.getProduct();
        product.getProductTaxes().removeIf(pt-> false);

        Tax tax = productTaxById.getTax();
        tax.getProductTaxes().removeIf(pt-> false);
    }

}
