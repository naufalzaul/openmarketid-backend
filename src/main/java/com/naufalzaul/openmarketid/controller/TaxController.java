package com.naufalzaul.openmarketid.controller;

import com.naufalzaul.openmarketid.constant.APIBash;
import com.naufalzaul.openmarketid.entity.Tax;
import com.naufalzaul.openmarketid.model.request.tax.TaxCreateRequest;
import com.naufalzaul.openmarketid.model.request.tax.TaxUpdateRequest;
import com.naufalzaul.openmarketid.model.response.CommonResponse;
import com.naufalzaul.openmarketid.model.response.TaxResponse;
import com.naufalzaul.openmarketid.service.TaxService;
import com.naufalzaul.openmarketid.service.mapper.TaxMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIBash.TAX)
@RequiredArgsConstructor
//@PreAuthorize("hasRole('ADMIN')")
public class TaxController {

    private final TaxService taxService;
    private final TaxMapper taxMapper;

    @PostMapping
    public ResponseEntity<CommonResponse<TaxResponse>> createTax(
            @RequestBody TaxCreateRequest taxCreateRequest) {

        TaxResponse taxResponse = taxService.createTax(taxCreateRequest);

        CommonResponse<TaxResponse> response = new CommonResponse<>(
                HttpStatus.CREATED.value(),
                "Tax successfully created",
                taxResponse
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<TaxResponse>>> findAllTaxes() {

        List<TaxResponse> taxResponses = taxService.findAllTaxes();

        CommonResponse<List<TaxResponse>> response = new CommonResponse<>(
                HttpStatus.OK.value(),
                "All products found",
                taxResponses
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping(APIBash.GET_BY_ID)
    public ResponseEntity<CommonResponse<TaxResponse>> findTaxById(@PathVariable String id) {

        Tax taxById = taxService.findTaxById(id);
        TaxResponse taxResponse = taxMapper.fromTax(taxById);
        CommonResponse<TaxResponse> response = new CommonResponse<>(
                HttpStatus.OK.value(),
                "All products found",
                taxResponse
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<TaxResponse>> updateTax(
            @RequestBody TaxUpdateRequest taxUpdateRequest) {

        TaxResponse taxResponse = taxService.updateTax(taxUpdateRequest);

        CommonResponse<TaxResponse> response = new CommonResponse<>(
                HttpStatus.CREATED.value(),
                "Tax successfully updated",
                taxResponse
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping(APIBash.GET_BY_ID)
    public ResponseEntity<CommonResponse<TaxResponse>> deleteTax(@PathVariable String id) {

        taxService.deleteTax(id);

        CommonResponse<TaxResponse> response = new CommonResponse<>(
                HttpStatus.OK.value(),
                "Product successfully deleted",
                null
        );

        return ResponseEntity.ok(response);
    }
}
