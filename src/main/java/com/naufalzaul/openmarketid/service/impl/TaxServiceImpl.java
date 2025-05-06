package com.naufalzaul.openmarketid.service.impl;

import com.naufalzaul.openmarketid.entity.Tax;
import com.naufalzaul.openmarketid.exception.DataNotFoundException;
import com.naufalzaul.openmarketid.model.request.tax.TaxCreateRequest;
import com.naufalzaul.openmarketid.model.request.tax.TaxUpdateRequest;
import com.naufalzaul.openmarketid.model.response.TaxResponse;
import com.naufalzaul.openmarketid.repository.TaxRepository;
import com.naufalzaul.openmarketid.service.ProductTaxService;
import com.naufalzaul.openmarketid.service.TaxService;
import com.naufalzaul.openmarketid.service.mapper.TaxMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class TaxServiceImpl implements TaxService {

    private final TaxRepository taxRepository;
    private final TaxMapper taxMapper;
    private final ProductTaxService productTaxService;

    @Override
    public TaxResponse createTax(TaxCreateRequest request) {
        taxRepository.findByDescription(request.getDescription())
                .ifPresent(existing -> {
                    throw new IllegalArgumentException(
                            format("Tax already exists with description '%s'",
                                   request.getDescription())
                    );
                });

        Tax createNewTax = Tax.builder()
                .description(request.getDescription())
                .taxPercentage(request.getTaxPercentage())
                .isActive(true)
                .build();

        Tax tax = taxRepository.save(createNewTax);
        return taxMapper.fromTax(tax);
    }


    @Override
    public List<TaxResponse> findAllTaxes() {
        return taxRepository.findAll().stream().map(taxMapper::fromTax).toList();
    }

    @Override
    public Tax findTaxById(String id) {
        return taxRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(
                        format("Tax not found with id %s", id)));
    }

    @Override
    public TaxResponse updateTax(TaxUpdateRequest request) {
        Tax findTax = findTaxById(request.getId());
        findTax.setDescription(findTax.getDescription());
        findTax.setTaxPercentage(findTax.getTaxPercentage());
        findTax.setIsActive(findTax.getIsActive());

        Tax tax = taxRepository.save(findTax);

        return taxMapper.fromTax(tax);
    }

    @Override
    public void deleteTax(String id) {
        Tax taxById = findTaxById(id);

        // Required cascade = CascadeType.ALL and orphanRemoval = true in entity
        taxById.getProductTaxes().clear();

        taxRepository.deleteById(id);
    }
}
