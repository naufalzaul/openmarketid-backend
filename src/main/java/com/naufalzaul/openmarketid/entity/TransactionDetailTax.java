package com.naufalzaul.openmarketid.entity;

import com.naufalzaul.openmarketid.constant.DBBash;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = DBBash.TRANSACTION_DETAIL_TAX_DB)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TransactionDetailTax {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "tax_percentage")
    private Double taxPercentage; // persentase pajaknya

    @ManyToOne(fetch = FetchType.LAZY)
    private Tax tax;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trx_detail_id")
    private TransactionDetail transactionDetail;
}
