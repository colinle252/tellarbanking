package com.tellarbanking.credit.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "company")
public class Company extends BaseEntity {
    @Column(name = "company_id", unique = true)
    private String companyId;

    @Column(name = "company_name")
    private String companyName;

//    private String address;
//    private String phone;
//    private String email;
}
