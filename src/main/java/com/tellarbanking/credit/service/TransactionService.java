package com.tellarbanking.credit.service;

import com.tellarbanking.credit.entity.Transaction;
import com.tellarbanking.credit.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Create a new transaction record
     *
     * @param transaction The transaction record to create.
     * @return The new transaction.
     */
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}