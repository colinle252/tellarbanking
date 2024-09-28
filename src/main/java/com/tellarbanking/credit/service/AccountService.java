package com.tellarbanking.credit.service;

import com.tellarbanking.credit.entity.Account;
import com.tellarbanking.credit.entity.Employee;
import com.tellarbanking.credit.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Create a new account.
     *
     * @param account input account parameters
     * @return The new account of an employee.
     */
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    /**
     * Get the credit balance of an employee.
     *
     * @param employee The employee parameters
     * @return The new account of an employee.
     */
    public Account getEmployeeCreditBalance(Employee employee) {
        return accountRepository.findByEmployeeId(employee.getId()).get();
    }

    /**
     * Find accounts which assigned to employees
     *
     * @param employees The employee list
     * @return The new account list those employees.
     */
    public List<Account> findAccountsByEmployees(List<Employee> employees) {
        return accountRepository.findByEmployeeIn(employees);
    }

    /**
     * Find accounts which assigned to employees
     *
     * @param employee The employee
     * @param balance The employee balance
     * @return The updated account of the employee.
     */
    public Account updateBalance(Employee employee, String balance) {
        return accountRepository.findByEmployeeId(employee.getId())
                .map(account -> {
                    account.setBalance(new BigDecimal(balance));
                    return accountRepository.save(account);
                })
                .orElseThrow(() -> new IllegalArgumentException("Account not found for employee: " + employee.getId()));
    }
}