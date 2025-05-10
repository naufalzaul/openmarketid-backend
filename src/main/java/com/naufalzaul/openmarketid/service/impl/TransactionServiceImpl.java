package com.naufalzaul.openmarketid.service.impl;

import com.naufalzaul.openmarketid.constant.PaymentMethod;
import com.naufalzaul.openmarketid.constant.TransactionStatus;
import com.naufalzaul.openmarketid.entity.*;
import com.naufalzaul.openmarketid.exception.DataNotFoundException;
import com.naufalzaul.openmarketid.model.request.transaction.TransactionDetailRequest;
import com.naufalzaul.openmarketid.model.request.transaction.TransactionFilterRequest;
import com.naufalzaul.openmarketid.model.request.transaction.TransactionRequest;
import com.naufalzaul.openmarketid.model.response.TransactionResponse;
import com.naufalzaul.openmarketid.repository.*;
import com.naufalzaul.openmarketid.service.CustomerService;
import com.naufalzaul.openmarketid.service.ProductService;
import com.naufalzaul.openmarketid.service.TransactionService;
import com.naufalzaul.openmarketid.service.mapper.TransactionMapper;
import com.naufalzaul.openmarketid.spesification.TransactionSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final CustomerRepository customerRepository;
    private final CustomerService customerService;
    private final ProductService productService;
    private final TransactionRepository transactionRepository;
    private final TransactionDetailRepository detailRepository;
    private final TransactionDetailTaxRepository detailTaxRepository;
    private final TransactionMapper transactionMapper;

    @Transactional(
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class
    )
    @Override
    public TransactionResponse createTransaction(TransactionRequest request) {
        Customer customer = customerService.findCustomerById(request.getCustomerId());

        double netAmount = 0.0;
        double totalTax = 0.0;
        List<TransactionDetail> transactionDetails = new ArrayList<>();

        Transaction transaction = Transaction.builder()
                .customer(customer)
                .transactionDate(LocalDateTime.now())
                .transactionStatus(TransactionStatus.NOT_PAID)
                .paymentMethod(PaymentMethod.findByMethod(request.getPaymentMethod()))
                .createdBy(customer.getName())
                .transactionDetails(new ArrayList<>())
                .build();

        transaction = transactionRepository.save(transaction);

        for (TransactionDetailRequest detailRequest : request.getProducts()) {
            Product product = productService.findProductById(detailRequest.getProductId());

            if (product.getAvailableQuantity() < detailRequest.getQuantity()) {
                throw new RuntimeException("Less quantity of product availability");
            }

            double productNetAmount = product.getPrice() * detailRequest.getQuantity();
            double taxAmount = 0.0;
            List<TransactionDetailTax> taxList = new ArrayList<>();

            for (ProductTax productTax : product.getProductTaxes()) {
                double percentage = productTax.getTax().getTaxPercentage();
                double taxForThis = productNetAmount * (percentage / 100);
                taxAmount += taxForThis;

                TransactionDetailTax tax = TransactionDetailTax.builder()
                        .tax(productTax.getTax())
                        .taxPercentage(percentage)
                        .build();

                taxList.add(tax);
            }

            TransactionDetail detail = TransactionDetail.builder()
                    .transaction(transaction)
                    .product(product)
                    .totalQuantity(detailRequest.getQuantity())
                    .taxAmount(taxAmount)
                    .transactionDetailTaxes(new ArrayList<>())
                    .build();

            detail = detailRepository.save(detail);

            for (TransactionDetailTax tax : taxList) {
                tax.setTransactionDetail(detail);
                detailTaxRepository.save(tax);
                detail.getTransactionDetailTaxes().add(tax);
            }

            netAmount += productNetAmount;
            totalTax += taxAmount;
            transactionDetails.add(detail);
        }

        transaction.setNetAmount(netAmount);
        transaction.setTotalTax(totalTax);
        transaction.setTotalAmount(netAmount + totalTax);
        transaction.setTransactionDetails(transactionDetails);
        transactionRepository.save(transaction);
        return transactionMapper.fromTransaction(transaction);
    }

    @Override
    public List<TransactionResponse> findAllTransactionsByUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Customer customer = customerRepository.findByUserEmail(email)
                .orElseThrow(() -> new DataNotFoundException("Customer not found with user email " + email));

        List<Transaction> transactions = transactionRepository.findByCustomer(customer);

        return transactions
                .stream()
                .map(transactionMapper::fromTransaction)
                .toList();
    }

    @Override
    public Page<TransactionResponse> filterTransaction(TransactionFilterRequest request) {

        if (request.getPage() <= 0) request.setPage(1);

        Sort sortBy = Sort.by(
                Sort.Direction.fromString(request.getDirection()), request.getSortBy()
        );

        Pageable pageable = PageRequest.of((request.getPage() - 1), request.getSize(), sortBy);


        Specification<Transaction> specification =
                TransactionSpecification.getSpecification(request);

        Page<Transaction> transactionPage = transactionRepository.findAll(specification, pageable);
        return transactionPage.map(transactionMapper::fromTransaction);
    }

    @Override
    public void canceledTransaction(String id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(
                        format("Transaction not found with id %s", id)));

        transaction.setTransactionStatus(TransactionStatus.CANCELLED);
    }
}
