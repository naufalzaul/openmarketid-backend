package com.naufalzaul.openmarketid.controller;

import com.naufalzaul.openmarketid.constant.APIBash;
import com.naufalzaul.openmarketid.constant.PaymentMethod;
import com.naufalzaul.openmarketid.constant.TransactionStatus;
import com.naufalzaul.openmarketid.entity.Transaction;
import com.naufalzaul.openmarketid.model.request.transaction.TransactionFilterRequest;
import com.naufalzaul.openmarketid.model.request.transaction.TransactionRequest;
import com.naufalzaul.openmarketid.model.response.CommonResponse;
import com.naufalzaul.openmarketid.model.response.TransactionResponse;
import com.naufalzaul.openmarketid.service.TransactionReportService;
import com.naufalzaul.openmarketid.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIBash.TRANSACTION)
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionReportService transactionReportService;

    @PostMapping
    public ResponseEntity<CommonResponse<TransactionResponse>> createTransaction(
            @RequestBody TransactionRequest transactionRequest
    ) {

        TransactionResponse transactionResponse = transactionService.createTransaction(transactionRequest);

        CommonResponse<TransactionResponse> response = new CommonResponse<>(
                HttpStatus.CREATED.value(),
                "Transaction successfully created",
                transactionResponse
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<Page<TransactionResponse>>> filterTransactions(
            @RequestParam(name = "customerName",required = false) String customerName,
            @RequestParam(name = "paymentMethod",required = false) String paymentMethod,
            @RequestParam(name = "transactionStatus",required = false) String transactionStatus,
            @RequestParam(name = "createdBy",required = false) String createdBy,
            @RequestParam(name = "startDate",required = false) String startDate,
            @RequestParam(name = "endDate",required = false) String endDate,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "transactionDate") String sortBy,
            @RequestParam(name = "direction", defaultValue = "desc") String direction
    ) {

        TransactionFilterRequest request = TransactionFilterRequest.builder()
                .customerName(customerName)
                .paymentMethod(paymentMethod)
                .transactionStatus(transactionStatus)
                .createdBy(createdBy)
                .startDate(startDate)
                .endDate(endDate)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .build();


        Page<TransactionResponse> transactionResponses = transactionService.filterTransaction(request);

        CommonResponse<Page<TransactionResponse>> response = new CommonResponse<>(
                HttpStatus.OK.value(),
                "Transactions found",
                transactionResponses
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping(APIBash.FIND_BY_AUTH_TOKEN)
    public ResponseEntity<CommonResponse<List<TransactionResponse>>> findAllTransactionsByUserEmail() {

        List<TransactionResponse> transactionResponses = transactionService.findAllTransactionsByUserEmail();

        CommonResponse<List<TransactionResponse>> response = new CommonResponse<>(
                HttpStatus.CREATED.value(),
                "Transactions found",
                transactionResponses
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/transactions/report-pdf")
    public ResponseEntity<ByteArrayResource> downloadTransactionPdf() {

        ByteArrayResource pdfReport = transactionReportService.generateTransactionPdf();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transaction_report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfReport);
    }

}
