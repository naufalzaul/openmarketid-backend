package com.naufalzaul.openmarketid.entity;

import com.naufalzaul.openmarketid.constant.DBBash;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = DBBash.TRANSACTION_DETAIL_DB)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TransactionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "total_qty", nullable = false)
    private Integer totalQuantity;

    @Column(name = "tax_amount", nullable = false)
    private Double taxAmount; //(harga produk * persentase pajak) / 100
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trx_id")
    private Transaction transaction;

    @OneToMany(mappedBy = "transactionDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionDetailTax> transactionDetailTaxes;
}
