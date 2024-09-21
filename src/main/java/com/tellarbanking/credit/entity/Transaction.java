package com.tellarbanking.credit.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "transaction")
public class Transaction extends BaseEntity {

    @Column(name = "transaction_id", unique = true)
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "amount")
    private BigDecimal amount;

//    private String transactionDate;
//    private String transactionType;
//    private String description;
}