package com.naufalzaul.openmarketid.entity;


import com.naufalzaul.openmarketid.constant.DBBash;
import com.naufalzaul.openmarketid.constant.PaymentMethod;
import com.naufalzaul.openmarketid.constant.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = DBBash.TRANSACTION_DB)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "net_amount")
    private Double netAmount;
    //bersih tanpa pajak -> total jumlah semua product dari hasil (harga produk * jumlah produk)
    //contoh: (5000 * 2) + (10000 * 4) = 50000

    @Column(name = "total_tax")
    private Double totalTax; // total tax amount 

    @Column(name = "total_amount")
    private Double totalAmount; //harga termasuk pajak -> net amount + total tax

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransactionStatus transactionStatus;

    @Column(name = "trx_date", nullable = false)
    private LocalDateTime transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "pay_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Column(name = "pay_date", nullable = true)
    private LocalDateTime paymentDate;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionDetail> transactionDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;
}
