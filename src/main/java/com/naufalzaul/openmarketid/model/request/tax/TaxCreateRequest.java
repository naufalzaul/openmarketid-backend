package com.naufalzaul.openmarketid.model.request.tax;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaxCreateRequest {
    @NotNull(message = "Description is required")
    private String description;

    @NotNull(message = "Tax percentage is required")
    private Double taxPercentage;
}
