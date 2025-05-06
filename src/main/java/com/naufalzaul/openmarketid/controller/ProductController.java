package com.naufalzaul.openmarketid.controller;

import com.naufalzaul.openmarketid.constant.APIBash;
import com.naufalzaul.openmarketid.model.request.product.ProductCreateRequest;
import com.naufalzaul.openmarketid.model.request.product.ProductUpdateRequest;
import com.naufalzaul.openmarketid.model.response.CommonResponse;
import com.naufalzaul.openmarketid.model.response.ProductResponse;
import com.naufalzaul.openmarketid.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIBash.PRODUCT)
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonResponse<ProductResponse>> createProduct(
            @RequestBody ProductCreateRequest productRequest) {

        ProductResponse productResponse = productService.createProduct(productRequest);

        CommonResponse<ProductResponse> response = new CommonResponse<>(
                HttpStatus.CREATED.value(),
                "Product successfully created",
                productResponse
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
//    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CommonResponse<List<ProductResponse>>> findAllProduct() {
        List<ProductResponse> productResponse = productService.findAllProducts();

        CommonResponse<List<ProductResponse>> response = new CommonResponse<>(
                HttpStatus.OK.value(),
                "All products found",
                productResponse
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonResponse<ProductResponse>> updateProduct(
            @RequestBody ProductUpdateRequest productRequest) {

        ProductResponse productResponse = productService.updateProduct(productRequest);

        CommonResponse<ProductResponse> response = new CommonResponse<>(
                HttpStatus.OK.value(),
                "Product successfully updated",
                productResponse
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(APIBash.GET_BY_ID)
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonResponse<ProductResponse>> deleteProduct(@PathVariable String id) {

        productService.deleteProduct(id);

        CommonResponse<ProductResponse> response = new CommonResponse<>(
                HttpStatus.OK.value(),
                "Product successfully deleted",
                null
        );

        return ResponseEntity.ok(response);
    }
}
