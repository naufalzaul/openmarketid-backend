package com.naufalzaul.openmarketid.repository;

import com.naufalzaul.openmarketid.entity.Customer;
import com.naufalzaul.openmarketid.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String>, JpaSpecificationExecutor<Transaction> {
    List<Transaction> findByCustomer(Customer customer);
}
