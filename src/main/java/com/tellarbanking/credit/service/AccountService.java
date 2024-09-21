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
     * Registers a new employee.
     *
     * @param account The ID of the company to which the employee belongs.
     * @return The registered employee.
     */
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account getEmployeeCreditBalance(Employee employee) {
        return accountRepository.findByEmployeeId(employee.getId()).get();
    }

    public List<Account> findAccountsByEmployees(List<Employee> employeeIds) {
        return accountRepository.findByEmployeeIn(employeeIds);
    }

    public Account updateBalance(Employee email, String balance) {
        Account account = accountRepository.findByEmployeeId(email.getId()).get();
        account.setBalance(new BigDecimal(balance));

        return accountRepository.save(account);
    }
}