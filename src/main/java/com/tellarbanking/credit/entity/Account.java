package com.tellarbanking.credit.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "account")
public class Account extends BaseEntity {
    @Column(name = "account_id", unique = true)
    private String accountId;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false, referencedColumnName = "id")
    private Employee employee;

    @Column(name = "balance")
    private BigDecimal balance;

    /*
    private String accountType;
    private String createdDate;
    private String status;
     */
}