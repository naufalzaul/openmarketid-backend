package com.naufalzaul.openmarketid.controller;

import com.naufalzaul.openmarketid.constant.APIBash;
import com.naufalzaul.openmarketid.model.request.payment.PaymentRequest;
import com.naufalzaul.openmarketid.model.response.CommonResponse;
import com.naufalzaul.openmarketid.model.response.TransactionResponse;
import com.naufalzaul.openmarketid.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(APIBash.PAYMENT)
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<CommonResponse<TransactionResponse>> payment(
            @RequestBody PaymentRequest paymentRequest) {

        TransactionResponse paymentResponse = paymentService.payment(paymentRequest);

        CommonResponse<TransactionResponse> response = new CommonResponse<>(
                HttpStatus.CREATED.value(),
                "Payment successfully",
                paymentResponse
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
