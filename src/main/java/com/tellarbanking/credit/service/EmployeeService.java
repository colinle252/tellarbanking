package com.tellarbanking.credit.service;

import com.tellarbanking.credit.converter.EmployeeConverter;
import com.tellarbanking.credit.dto.EmployeeDTO;
import com.tellarbanking.credit.entity.Account;
import com.tellarbanking.credit.entity.Employee;
import com.tellarbanking.credit.entity.Transaction;
import com.tellarbanking.credit.model.request.EmployeeRequest;
import com.tellarbanking.credit.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeConverter employeeConverter;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    /**
     * Registers a new employee, notice that this action should be under 1 transaction
     * because we're inserting different info into the database such as a new employee, a new account info, a new transaction
     * then need to comply ACID concept here
     *
     * @return The registered employee.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public EmployeeDTO registerEmployee(EmployeeRequest request) {
        /* In the real world should validate the company and other validation before register
           an employee and account
        // Check if the company exists
        Optional<Company> companyOpt = companyRepository.findByName(companyName);
        if (!companyOpt.isPresent()) {
            throw new RuntimeException("Company not found with ID: " + companyId);
        }
        */


        // Create a new employee object
        Employee employee = new Employee();
        // can design to generate ID based on company format, now just generate unique one
        employee.setEmployeeId(UUID.randomUUID().toString());
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        //employee.setCompany(Company.builder().companyName(request.getCompanyName()).build());

        // Save the employee to the database
        Employee employeeData = employeeRepository.save(employee);

        // Create employee's account
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(1000));
        account.setEmployee(employee);
        accountService.createAccount(account);

        // Create transaction record of the action of adding an account with a new balance
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(account.getBalance());
        transactionService.createTransaction(transaction);

        return employeeConverter.convert(employeeData);

    }

    /**
     * Check credit balance of the employee
     *
     * @param request The employee's information.
     * @return The employee's with balance of account.
     */
    public EmployeeDTO checkCreditBalance(EmployeeRequest request) {
        Employee employee = employeeRepository.findByEmail(request.getEmail()).get();

        Account account = accountService.getEmployeeCreditBalance(employee);

        EmployeeDTO employeeDTO = employeeConverter.convert(employee);
        employeeDTO.setBalance(account.getBalance());

        return employeeDTO;
    }

    /**
     * Get all employees with their balance
     *
     * @param pageable The paging and sorting of the expected result.
     * @return The employee list with balance of account.
     */
    public List<EmployeeDTO> getAllEmployeesWithBalance(Pageable pageable) {
        Page<Employee> employeePage = employeeRepository.findAll(pageable);

        List<Account> accounts = accountService.findAccountsByEmployees(employeePage.getContent());

        Map<UUID, BigDecimal> accountBalanceMap = accounts.stream()
                .collect(Collectors.toMap(account -> account.getEmployee().getId(), Account::getBalance));

        return employeePage.getContent().stream()
                .map(employee -> {
                    EmployeeDTO employeeDTO = employeeConverter.convert(employee);
                    employeeDTO.setBalance(accountBalanceMap.get(employee.getId()));
                    return employeeDTO;
                }).collect(Collectors.toList());
    }

    /**
     * Update balance of an employee
     * because we're inserting different info into the database such as updating account balance & new transaction record
     * then need to comply ACID concept here
     *
     * @return The registered employee.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public EmployeeDTO updateBalance(EmployeeRequest request) {
        Employee employee = employeeRepository.findByEmail(request.getEmail()).get();

        Account account = accountService.updateBalance(employee, request.getBalance());

        // Create transaction record of the action of updating the balance
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(account.getBalance());
        transactionService.createTransaction(transaction);

        EmployeeDTO employeeDTO = employeeConverter.convert(employee);
        employeeDTO.setBalance(account.getBalance());

        return employeeDTO;
    }

}