package com.tellarbanking.credit.dto;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
@Data
public class BaseDTO implements Serializable {
    private static final long serialVersionUID = -6849794470754667711L;

    private UUID id;
}
