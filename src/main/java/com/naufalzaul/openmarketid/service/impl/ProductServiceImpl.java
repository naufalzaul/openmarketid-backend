package com.naufalzaul.openmarketid.service.impl;

import com.naufalzaul.openmarketid.entity.Product;
import com.naufalzaul.openmarketid.entity.ProductTax;
import com.naufalzaul.openmarketid.entity.Tax;
import com.naufalzaul.openmarketid.exception.DataNotFoundException;
import com.naufalzaul.openmarketid.model.request.product.ProductCreateRequest;
import com.naufalzaul.openmarketid.model.request.product.ProductUpdateRequest;
import com.naufalzaul.openmarketid.model.response.ProductResponse;
import com.naufalzaul.openmarketid.repository.ProductRepository;
import com.naufalzaul.openmarketid.repository.ProductTaxRepository;
import com.naufalzaul.openmarketid.repository.TaxRepository;
import com.naufalzaul.openmarketid.service.ProductService;
import com.naufalzaul.openmarketid.service.TaxService;
import com.naufalzaul.openmarketid.service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductTaxRepository productTaxRepository;
    private final TaxRepository taxRepository;
    private final TaxService taxService;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse createProduct(ProductCreateRequest request) {
        List<ProductTax> productTaxes = new ArrayList<>();

        Product createProduct = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .availableQuantity(request.getAvailableQuantity())
                .productTaxes(productTaxes)
                .build();

        Product product = productRepository.save(createProduct);

        if (!request.getTaxIds().isEmpty()) {
            for (String taxResponse : request.getTaxIds()) {
                Tax taxById = taxService.findTaxById(taxResponse);
                ProductTax productTax = ProductTax.builder()
                        .product(createProduct)
                        .tax(taxById)
                        .isActive(true)
                        .build();
                productTaxes.add(productTax);
                taxById.getProductTaxes().add(productTax);
            }
        }

        productTaxRepository.saveAll(productTaxes);
        product.setProductTaxes(productTaxes);

        return productMapper.fromProduct(product);
    }

    @Override
    public List<ProductResponse> findAllProducts() {
        return productRepository.findAll()
                .stream().map(productMapper::fromProduct).toList();
    }

    @Override
    public Product findProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(
                        format("Product not found with id %s", id)));
    }

    @Override
    public ProductResponse updateProduct(ProductUpdateRequest request) {
        Product productById = findProductById(request.getId());
        productById.setName(request.getName());
        productById.setPrice(request.getPrice());
        productRepository.save(productById);
        return productMapper.fromProduct(productById);
    }

    @Override
    public void deleteProduct(String id) {
        Product productById = findProductById(id);

        // Required cascade = CascadeType.ALL and orphanRemoval = true in entity
        productById.getProductTaxes().clear();

        productRepository.deleteById(id);
    }
}
