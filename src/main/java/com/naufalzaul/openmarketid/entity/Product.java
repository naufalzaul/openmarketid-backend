package com.naufalzaul.openmarketid.entity;


import com.naufalzaul.openmarketid.constant.DBBash;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = DBBash.PRODUCT_DB)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "avail_qty", nullable = false)
    private Integer availableQuantity;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductTax> productTaxes;

}
