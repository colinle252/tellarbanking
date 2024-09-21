package com.tellarbanking.credit.repository;

import com.tellarbanking.credit.entity.Account;
import com.tellarbanking.credit.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByEmployeeId(UUID id);

    List<Account> findByEmployeeIn(List<Employee> employeeIds);
}
