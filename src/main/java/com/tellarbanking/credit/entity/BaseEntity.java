package com.tellarbanking.credit.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
@Data
public class BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -6849794470754667710L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
}
