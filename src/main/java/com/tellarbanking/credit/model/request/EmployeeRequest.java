package com.tellarbanking.credit.model.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class EmployeeRequest {

    private String companyName;

    private String name;

    @Email
    private String email;

    private String balance;

//    private String dateOfBirth;
//    private String phone;
//    private String hireDate;
}
