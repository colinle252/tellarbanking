package com.tellarbanking.credit.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenericException {
    private String status;
    private String message;
}
