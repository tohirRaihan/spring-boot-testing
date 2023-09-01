package com.tohir.springboot.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.tohir.springboot.entity.Employee;
import com.tohir.springboot.exception.ResourceNotFoundException;
import com.tohir.springboot.repository.EmployeeRepository;
import com.tohir.springboot.service.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService {

    // @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        
        Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (savedEmployee.isPresent()) {
            throw new ResourceNotFoundException("Employee with this mail already exists");
        }
        
        return employeeRepository.save(employee);
    }
    
}
