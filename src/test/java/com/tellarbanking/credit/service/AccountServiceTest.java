package com.tellarbanking.credit.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.tellarbanking.credit.entity.Account;
import com.tellarbanking.credit.entity.Employee;
import com.tellarbanking.credit.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private Account account;
    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setEmail("test@example.com");

        account = new Account();
        account.setId(UUID.randomUUID());
        account.setEmployee(employee);
        account.setBalance(BigDecimal.ZERO);
    }

    @Test
    void testCreateAccount() {
        when(accountRepository.save(account)).thenReturn(account);

        Account createdAccount = accountService.createAccount(account);

        assertNotNull(createdAccount);
        assertEquals(account.getId(), createdAccount.getId());
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void testGetEmployeeCreditBalance() {
        when(accountRepository.findByEmployeeId(employee.getId())).thenReturn(Optional.of(account));

        Account foundAccount = accountService.getEmployeeCreditBalance(employee);

        assertNotNull(foundAccount);
        assertEquals(account.getId(), foundAccount.getId());
        verify(accountRepository, times(1)).findByEmployeeId(employee.getId());
    }

    @Test
    void testFindAccountsByEmployees() {
        List<Employee> employees = Arrays.asList(employee);
        List<Account> accounts = Arrays.asList(account);

        when(accountRepository.findByEmployeeIn(employees)).thenReturn(accounts);

        List<Account> foundAccounts = accountService.findAccountsByEmployees(employees);

        assertNotNull(foundAccounts);
        assertEquals(1, foundAccounts.size());
        verify(accountRepository, times(1)).findByEmployeeIn(employees);
    }

    @Test
    void testUpdateBalance() {
        when(accountRepository.findByEmployeeId(employee.getId())).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);

        Account updatedAccount = accountService.updateBalance(employee, "100.00");

        assertNotNull(updatedAccount);
        assertEquals(new BigDecimal("100.00"), updatedAccount.getBalance());
        verify(accountRepository, times(1)).findByEmployeeId(employee.getId());
        verify(accountRepository, times(1)).save(account);
    }
}

