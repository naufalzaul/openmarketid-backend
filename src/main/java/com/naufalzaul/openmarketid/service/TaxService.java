package com.naufalzaul.openmarketid.service;

import com.naufalzaul.openmarketid.entity.Tax;
import com.naufalzaul.openmarketid.model.request.tax.TaxCreateRequest;
import com.naufalzaul.openmarketid.model.request.tax.TaxUpdateRequest;
import com.naufalzaul.openmarketid.model.response.TaxResponse;

import java.util.List;

public interface TaxService {
    TaxResponse createTax(TaxCreateRequest request);
    List<TaxResponse> findAllTaxes();
    Tax findTaxById(String id);
    TaxResponse updateTax(TaxUpdateRequest request);
    void deleteTax(String id);
}
