package com.naufalzaul.openmarketid.service.impl;

import com.naufalzaul.openmarketid.constant.PaymentMethod;
import com.naufalzaul.openmarketid.constant.TransactionStatus;
import com.naufalzaul.openmarketid.entity.Customer;
import com.naufalzaul.openmarketid.entity.Product;
import com.naufalzaul.openmarketid.entity.Transaction;
import com.naufalzaul.openmarketid.entity.TransactionDetail;
import com.naufalzaul.openmarketid.exception.DataNotFoundException;
import com.naufalzaul.openmarketid.model.request.transaction.TransactionDetailRequest;
import com.naufalzaul.openmarketid.model.request.transaction.TransactionFilterRequest;
import com.naufalzaul.openmarketid.model.request.transaction.TransactionRequest;
import com.naufalzaul.openmarketid.model.response.TransactionResponse;
import com.naufalzaul.openmarketid.repository.*;
import com.naufalzaul.openmarketid.service.CustomerService;
import com.naufalzaul.openmarketid.service.ProductService;
import com.naufalzaul.openmarketid.service.TransactionDetailService;
import com.naufalzaul.openmarketid.service.TransactionService;
import com.naufalzaul.openmarketid.service.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
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

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final ProductTaxRepository productTaxRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionDetailService transactionDetailService;
    private final TransactionMapper transactionMapper;


    @Transactional
    @Override
    public TransactionResponse createTransaction(TransactionRequest request) {
        Customer customerById = customerService.findCustomerById(request.getCustomerId());

        System.out.println(customerById);

        double netAmount = 0.0;
        double totalTax = 0.0;
        double totalAmount = 0.0;

        Transaction createNewTransaction = Transaction.builder()
                .customer(customerById)
                .transactionDate(LocalDateTime.now())
                .transactionStatus(TransactionStatus.NOT_PAID)
                .paymentMethod(PaymentMethod.findByMethod(request.getPaymentMethod()))
                .transactionDetails(new ArrayList<>())
                .build();

        Transaction transaction = transactionRepository.save(createNewTransaction);

        for (TransactionDetailRequest detailRequest : request.getProducts()) {
            TransactionDetail transactionDetail = transactionDetailService
                    .createTransactionDetail(detailRequest, transaction);
            Product product = transactionDetail.getProduct();

            if (product.getAvailableQuantity() < detailRequest.getQuantity()) {
                throw new RuntimeException("Less quantity of product availability");
            }

            double productNetAmount = product.getPrice() * detailRequest.getQuantity();
            double productTaxAmount = transactionDetail.getTaxAmount();

            netAmount += productNetAmount;
            totalTax += productTaxAmount;

            transaction.getTransactionDetails().add(transactionDetail);
        }

        totalAmount = netAmount + totalTax;

        transaction.setNetAmount(netAmount);
        transaction.setTotalTax(totalTax);
        transaction.setTotalAmount(totalAmount);
        transaction.setCreatedBy(customerById.getName());

        transactionRepository.save(transaction);

        return transactionMapper.fromTransaction(transaction);
    }

    @Override
    public List<TransactionResponse> findAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(transactionMapper::fromTransaction).toList();
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
    public Page<Transaction> filterTransaction(TransactionFilterRequest request) {

        if (request.getPage() <= 0) request.setPage(1);

        System.out.println(request);
        return null;
        //        Sort sortBy = Sort.by(
        //                Sort.Direction.fromString(request.getDirection()), request.getSortBy()
        //        );
        //
        //        Pageable pageable = PageRequest.of((request.getPage() - 1), request.getSize(), sortBy);
        //
        //
        //        Specification<Transaction> specification =
        //                TransactionSpecification.getSpecification(request);
        //
        //        return transactionRepository.findAll(specification, pageable);
        //        return transactions.map(transactionMapper::fromTransaction);
    }

    @Override
    public void canceledTransaction(String id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(
                        format("Transaction not found with id %s", id)));

        //        transaction.setPaymentStatus(PaymentStatus.CANCELLED);
    }
}
