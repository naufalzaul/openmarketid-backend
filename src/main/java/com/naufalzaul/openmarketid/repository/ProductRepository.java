package com.naufalzaul.openmarketid.repository;

import com.naufalzaul.openmarketid.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
}
