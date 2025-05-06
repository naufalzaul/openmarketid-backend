package com.naufalzaul.openmarketid.entity;


import com.naufalzaul.openmarketid.constant.DBBash;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = DBBash.TAX_DB)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Tax {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "tax_percentage")
    private Double taxPercentage;

    private Boolean isActive;

    @OneToMany(mappedBy = "tax", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductTax> productTaxes;
}
