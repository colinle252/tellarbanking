package com.tellarbanking.credit.repository;

import com.tellarbanking.credit.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
//    List<Transaction> findByAccount_AccountId(Long accountId);
}