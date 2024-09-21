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
     * Registers a new employee.
     *
     * @param transaction The ID of the company to which the employee belongs.
     * @return The registered employee.
     */
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}