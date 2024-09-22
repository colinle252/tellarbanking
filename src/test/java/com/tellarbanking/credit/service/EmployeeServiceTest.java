package com.tellarbanking.credit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.tellarbanking.credit.converter.EmployeeConverterTest;
import com.tellarbanking.credit.dto.EmployeeDTO;
import com.tellarbanking.credit.entity.Account;
import com.tellarbanking.credit.entity.Employee;
import com.tellarbanking.credit.entity.Transaction;
import com.tellarbanking.credit.model.request.EmployeeRequest;
import com.tellarbanking.credit.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private AccountService accountService;

    @Mock
    private TransactionService transactionService;

    @Mock
    private EmployeeConverterTest employeeConverterTest;

    @InjectMocks
    private EmployeeService employeeService;

    private EmployeeRequest request;

    private Employee employee;
    private Account account;
    private EmployeeDTO employeeDTO;

    @BeforeEach
    void setUp() {
        request = new EmployeeRequest();
        request.setName("John Doe");
        request.setEmail("john.doe@example.com");

        employee = new Employee();
        employee.setEmail(request.getEmail());

        account = new Account();
        account.setBalance(BigDecimal.valueOf(1000));

        employeeDTO = new EmployeeDTO();
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void testRegisterEmployee() {
        Employee employee = new Employee();
        employee.setEmployeeId(UUID.randomUUID().toString());
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());

        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(1000));
        account.setEmployee(employee);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(account.getBalance());

        EmployeeDTO employeeDTO = new EmployeeDTO();

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(employeeConverterTest.convert(any(Employee.class))).thenReturn(employeeDTO);

        EmployeeDTO result = employeeService.registerEmployee(request);

        verify(employeeRepository, times(1)).save(any(Employee.class));
        verify(accountService, times(1)).createAccount(any(Account.class));
        verify(transactionService, times(1)).createTransaction(any(Transaction.class));
        verify(employeeConverterTest, times(1)).convert(any(Employee.class));

        assertNotNull(result);
    }

    @Test
    void testCheckCreditBalance() {
        when(employeeRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(employee));
        when(accountService.getEmployeeCreditBalance(employee)).thenReturn(account);
        when(employeeConverterTest.convert(employee)).thenReturn(employeeDTO);

        EmployeeDTO result = employeeService.checkCreditBalance(request);

        verify(employeeRepository, times(1)).findByEmail(request.getEmail());
        verify(accountService, times(1)).getEmployeeCreditBalance(employee);
        verify(employeeConverterTest, times(1)).convert(employee);

        assertNotNull(result);
        assertEquals(account.getBalance(), result.getBalance());
    }

    @Test
    public void testGetAllEmployeesWithBalance() {
        Pageable pageable = PageRequest.of(0, 10);
        Employee employee1 = new Employee();
        employee1.setId(UUID.randomUUID());
        Employee employee2 = new Employee();
        employee2.setId(UUID.randomUUID());

        List<Employee> employees = Arrays.asList(employee1, employee2);
        Page<Employee> employeePage = new PageImpl<>(employees);

        when(employeeRepository.findAll(pageable)).thenReturn(employeePage);

        Account account1 = new Account();
        account1.setEmployee(employee1);
        account1.setBalance(BigDecimal.valueOf(1000));
        Account account2 = new Account();
        account2.setEmployee(employee2);
        account2.setBalance(BigDecimal.valueOf(2000));

        when(accountService.findAccountsByEmployees(employees)).thenReturn(Arrays.asList(account1, account2));

        EmployeeDTO employeeDTO1 = new EmployeeDTO();
        EmployeeDTO employeeDTO2 = new EmployeeDTO();

        when(employeeConverterTest.convert(employee1)).thenReturn(employeeDTO1);
        when(employeeConverterTest.convert(employee2)).thenReturn(employeeDTO2);

        List<EmployeeDTO> result = employeeService.getAllEmployeesWithBalance(pageable);

        assertEquals(2, result.size());
        assertEquals(BigDecimal.valueOf(2000), result.get(0).getBalance());
        assertEquals(BigDecimal.valueOf(2000), result.get(1).getBalance());
    }

    @Test
    public void testUpdateBalance() {
        EmployeeRequest request = new EmployeeRequest();
        request.setEmail("test@example.com");
        request.setBalance("1500");

        Employee employee = new Employee();
        employee.setEmail("test@example.com");

        when(employeeRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(employee));

        Account account = new Account();
        account.setEmployee(employee);
        account.setBalance(BigDecimal.valueOf(1500));

        when(accountService.updateBalance(employee, request.getBalance())).thenReturn(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(account.getBalance());

        EmployeeDTO employeeDTO = new EmployeeDTO();
        when(employeeConverterTest.convert(employee)).thenReturn(employeeDTO);

        EmployeeDTO result = employeeService.updateBalance(request);

        assertEquals(BigDecimal.valueOf(1500), result.getBalance());
        verify(transactionService, times(1)).createTransaction(any(Transaction.class));
    }
}
