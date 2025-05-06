package com.naufalzaul.openmarketid.repository;

import com.naufalzaul.openmarketid.entity.TransactionDetailTax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailTaxRepository extends JpaRepository<TransactionDetailTax, String> {}
