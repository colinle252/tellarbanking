package com.tellarbanking.credit.converter;

import com.tellarbanking.credit.dto.EmployeeDTO;
import com.tellarbanking.credit.entity.Employee;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmployeeConverter implements Converter<Employee, EmployeeDTO> {

    private ModelMapper modelMapper;

    @Override
    public EmployeeDTO convert(Employee source) {
        return modelMapper.map(source, EmployeeDTO.class);
    }
}
