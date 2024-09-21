package com.tellarbanking.credit.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "employee")
public class Employee extends BaseEntity {
    @Column(name = "employee_id", unique = true)
    private String employeeId;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company company;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

// @Column(name = "first_name")
////    private String firstName;
////
////    @Column(name = "last_name")
////    private String lastName;

//    @Column(name = "date_of_birth")
//    private String dateOfBirth;

//    @Column(name = "phone")
//    private String phone;

}