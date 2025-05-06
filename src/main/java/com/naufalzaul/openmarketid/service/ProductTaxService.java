package com.naufalzaul.openmarketid.service;

import com.naufalzaul.openmarketid.entity.ProductTax;

public interface ProductTaxService {
    ProductTax findProductTaxById(String id);
    void deleteProductTax(String id);
}
