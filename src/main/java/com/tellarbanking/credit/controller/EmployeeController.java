package com.tellarbanking.credit.controller;

import com.tellarbanking.credit.dto.EmployeeDTO;
import com.tellarbanking.credit.model.request.EmployeeRequest;
import com.tellarbanking.credit.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/api/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    /**
     * Endpoint to register a new employee.
     *
     * @param request The employee registration request.
     * @return The registered employee.
     */
    @PostMapping("/register")
    public ResponseEntity<EmployeeDTO> registerEmployee(@Validated @RequestBody EmployeeRequest request) {
        // Call the service to register the employee
//        Employee registeredEmployee = employeeService.registerEmployee(request);

        return ResponseEntity.ok(employeeService.registerEmployee(request));
    }

    /**
     * Endpoint to register a new employee.
     *
     * @param request The employee registration request.
     * @return The registered employee.
     */
    @PostMapping("/checkBalance")
    public ResponseEntity<EmployeeDTO> getCreditBalance(@Validated @RequestBody EmployeeRequest request) {
        // Call the service to register the employee
        return ResponseEntity.ok(employeeService.checkCreditBalance(request));
    }

    @GetMapping("/allBalance")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployeeBalance(Pageable pageable) {
        return ResponseEntity.ok(employeeService.getAllEmployeesWithBalance(pageable));
    }


    /**
     * Endpoint to register a new employee.
     *
     * @param request The employee registration request.
     * @return The registered employee.
     */
    @PostMapping("/updateBalance")
    public ResponseEntity<EmployeeDTO> updateBalance(@Validated @RequestBody EmployeeRequest request) {
        return ResponseEntity.ok(employeeService.updateBalance(request));
    }


//    @Autowired
//    private EmployeeService employeeService;
//
//    // Register a new company
//    @PostMapping("/company")
//    public Mono<ResponseEntity<Object>> registerCompany(@RequestBody Company company) {
//        return employeeService.registerCompany(company)
//                .map(savedCompany -> ResponseEntity.ok(savedCompany))
//                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(null)));
//    }
//
//    // Register a new employee
//    @PostMapping("/employee")
//    public Mono<ResponseEntity<Object>> registerEmployee(@RequestBody Employee employee) {
//        return employeeService.registerEmployee(employee)
//                .map(savedEmployee -> ResponseEntity.ok(savedEmployee))
//                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(null)));
//    }
//
//    // Get employee credit balance by email
//    @GetMapping("/employee/credit")
//    public Mono<ResponseEntity<Double>> getEmployeeCredit(@RequestParam String email) {
//        return employeeService.getEmployeeByEmail(email)
//                .map(employee -> ResponseEntity.ok(employee.getCreditBalance()))
//                .defaultIfEmpty(ResponseEntity.notFound().build());
//    }
//
//    // Get all employees
//    @GetMapping("/employees")
//    public Flux<Employee> getAllEmployees() {
//        return employeeService.getAllEmployees();
//    }
//
//    // Update employee credit balance
//    @PostMapping("/employee/updateCredit")
//    public Mono<ResponseEntity<Employee>> updateEmployeeCredit(@RequestParam String email, @RequestParam Double amount, @RequestParam String description) {
//        return employeeService.updateEmployeeCredit(email, amount, description)
//                .map(updatedEmployee -> ResponseEntity.ok(updatedEmployee))
//                .defaultIfEmpty(ResponseEntity.notFound().build());
//    }

    // Get all transactions for an employee
//    @GetMapping("/employee/transactions")
//    public Flux<CreditTransaction> getTransactionsByEmployee(@RequestParam Long employeeId) {
//        return employeeService.getTransactionsByEmployeeId(employeeId);
//    }
}


