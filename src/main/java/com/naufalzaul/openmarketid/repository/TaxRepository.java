package com.naufalzaul.openmarketid.repository;

import com.naufalzaul.openmarketid.entity.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaxRepository extends JpaRepository<Tax, String> {

    Optional<Tax> findByDescription(String description);
}
