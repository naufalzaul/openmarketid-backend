package com.naufalzaul.openmarketid.service;

import com.naufalzaul.openmarketid.entity.Product;
import com.naufalzaul.openmarketid.model.request.product.ProductCreateRequest;
import com.naufalzaul.openmarketid.model.request.product.ProductUpdateRequest;
import com.naufalzaul.openmarketid.model.response.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductCreateRequest request);
    List<ProductResponse> findAllProducts();
    Product findProductById(String id);
    ProductResponse updateProduct(ProductUpdateRequest request);
    void deleteProduct(String id);
}
