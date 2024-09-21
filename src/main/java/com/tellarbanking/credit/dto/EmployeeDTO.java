package com.tellarbanking.credit.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmployeeDTO extends BaseDTO {

    private String employeeId;

    private String email;

    private String name;

    private BigDecimal balance;
}
