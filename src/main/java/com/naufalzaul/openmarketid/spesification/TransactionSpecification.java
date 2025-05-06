package com.naufalzaul.openmarketid.spesification;

import com.naufalzaul.openmarketid.entity.Transaction;
import com.naufalzaul.openmarketid.model.request.transaction.TransactionFilterRequest;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


@Slf4j
public class TransactionSpecification {
    public static Specification<Transaction> getSpecification(
            TransactionFilterRequest request
    ) {
        return (((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
//            System.out.println(request);
//            // By date range
//            if (request.getStartDate() != null && request.getEndDate() != null) {
//                predicates.add(criteriaBuilder.between(
//                        root.get("transactionDate"),
//                        LocalDate.parse(request.getStartDate()).atStartOfDay(),
//                        LocalDate.parse(request.getEndDate()).atTime(23, 59, 59)
//                ));
//            }
//
//            // By customer name
//            if (request.getCustomerName() != null && !request.getCustomerName().isBlank()) {
//                predicates.add(criteriaBuilder.like(
//                        criteriaBuilder.lower(
//                                root.get("customer").get("name")
//                        ), "%" + request.getCustomerName().toLowerCase() + "%"
//                ));
//            }
//
//            // By payment method
//            if (request.getPaymentMethod() != null) {
//                predicates.add(criteriaBuilder.equal(root.get("paymentMethod"), request.getPaymentMethod()));
//            }
//
//            // By transaction status
//            if (request.getTransactionStatus() != null) {
//                predicates.add(criteriaBuilder.equal(root.get("transactionStatus"), request.getPaymentMethod()));
//            }
//
//              // By created by
//            if (request.getCreatedBy() != null &&
//                    !request.getCreatedBy().isBlank()) {
//                predicates.add(criteriaBuilder.equal(root.get("createdBy"), request.getCreatedBy()));
//            }

            assert query != null;
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        }));
    }
}
