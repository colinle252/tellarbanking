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
     * Registers a new employee.
     *
     * @return The registered employee.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public EmployeeDTO registerEmployee(EmployeeRequest request) {
        // Check if the company exists
//        Optional<Company> companyOpt = companyRepository.findById(companyId);
//        if (!companyOpt.isPresent()) {
//            throw new RuntimeException("Company not found with ID: " + companyId);
//        }

        // Create a new employee object
        Employee employee = new Employee();
        // can design to generate ID based on company format, now just generate unique one
        employee.setEmployeeId(UUID.randomUUID().toString());
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());


        // Save the employee to the database
        Employee employeeData = employeeRepository.save(employee);


        // Save Account to the database
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(1000));
        account.setEmployee(employee);
        accountService.createAccount(account);

        // Save Transaction to the database
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(account.getBalance());
        transactionService.createTransaction(transaction);

        return employeeConverter.convert(employeeData);

    }

    /**
     * Registers a new employee.
     *
     * @param request The ID of the company to which the employee belongs.
     * @return The registered employee.
     */
    public EmployeeDTO checkCreditBalance(EmployeeRequest request) {
        Employee employee = employeeRepository.findByEmail(request.getEmail()).get();

        Account account = accountService.getEmployeeCreditBalance(employee);

        EmployeeDTO employeeDTO = employeeConverter.convert(employee);
        employeeDTO.setBalance(account.getBalance());

        return employeeDTO;
    }

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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public EmployeeDTO updateBalance(EmployeeRequest request) {
        Employee employee = employeeRepository.findByEmail(request.getEmail()).get();

        Account account = accountService.updateBalance(employee, request.getBalance());

        EmployeeDTO employeeDTO = employeeConverter.convert(employee);
        employeeDTO.setBalance(account.getBalance());

        return employeeDTO;
    }

}