package com.naufalzaul.openmarketid.repository;

import com.naufalzaul.openmarketid.entity.ProductTax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductTaxRepository extends JpaRepository<ProductTax, String> {

    @Query("""
        SELECT pt.tax.taxPercentage\s
        FROM ProductTax pt\s
        WHERE pt.product.id = :productId AND pt.isActive = true
   \s""")
    List<Double> findTaxPercentagesByProductId(@Param("productId") String productId);
}
