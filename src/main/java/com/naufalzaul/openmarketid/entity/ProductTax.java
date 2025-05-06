package com.naufalzaul.openmarketid.entity;


import com.naufalzaul.openmarketid.constant.DBBash;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = DBBash.PRODUCT_TAX_DB)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductTax {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "tax_id", nullable = false)
    private Tax tax;

    private Boolean isActive;
}
