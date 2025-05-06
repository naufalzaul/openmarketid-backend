package com.naufalzaul.openmarketid.config.seeder;
import com.naufalzaul.openmarketid.entity.Tax;
import com.naufalzaul.openmarketid.model.request.product.ProductCreateRequest;
import com.naufalzaul.openmarketid.repository.ProductRepository;
import com.naufalzaul.openmarketid.repository.TaxRepository;
import com.naufalzaul.openmarketid.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Order(4)
public class ProductSeeder implements CommandLineRunner {
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final TaxRepository taxRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        List<Tax> taxes = taxRepository.findAll();

        Map<String, String> taxMap = taxes.stream().collect(Collectors.toMap(Tax::getDescription, Tax::getId));

        if (productRepository.count() == 0) {
            productService.createProduct(
                    ProductCreateRequest.builder()
                            .name("Laptop Lenovo")
                            .price(10_000_000.0)
                            .availableQuantity(3456)
                            .taxIds(List.of(
                                    taxMap.get("PPN"),
                                    taxMap.get("PPH")
                            ))
                            .build()
            );
            productService.createProduct(
                    ProductCreateRequest.builder()
                            .name("Chair Gaming")
                            .price(6_800_000.0)
                            .availableQuantity(923)
                            .taxIds(List.of(
                                    taxMap.get("Pajak Barang Mewah"),
                                    taxMap.get("Bebas Pajak")
                            ))
                            .build()
            );
            productService.createProduct(
                    ProductCreateRequest.builder()
                            .name("Keyboard Mechanical")
                            .price(750_000.0)
                            .availableQuantity(345)
                            .taxIds(List.of(
                                    taxMap.get("PPN"),
                                    taxMap.get("Jasa")
                            ))
                            .build()
            );
            System.out.println("Product data seeded.");
        } else {
            System.out.println("Product data already exists, skipping seed.");
        }
    }
}
