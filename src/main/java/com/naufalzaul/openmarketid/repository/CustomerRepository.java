package com.naufalzaul.openmarketid.repository;

import com.naufalzaul.openmarketid.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByUserEmail(String email);
}
