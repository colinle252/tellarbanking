package com.tellarbanking.credit.converter;

import com.tellarbanking.credit.dto.EmployeeDTO;
import com.tellarbanking.credit.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeConverterTest {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeConverter employeeConverter;

    private Employee employee;
    private EmployeeDTO employeeDTO;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setName("John Doe");

        employeeDTO = new EmployeeDTO();
        employeeDTO.setId(UUID.randomUUID());
        employeeDTO.setName("John Doe");
    }

    @Test
    void testConvert() {
        when(modelMapper.map(employee, EmployeeDTO.class)).thenReturn(employeeDTO);

        EmployeeDTO result = employeeConverter.convert(employee);

        assertEquals(employeeDTO, result);
    }
}

