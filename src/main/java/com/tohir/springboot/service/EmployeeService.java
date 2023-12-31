package com.tohir.springboot.service;

import java.util.List;
import java.util.Optional;

import com.tohir.springboot.entity.Employee;

public interface EmployeeService {
    
    Employee saveEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Optional<Employee> getEmployeeById(Long id);

    Employee updateEmployee(Employee updatedEmployee);

    void deleteEmployee(Long id);
    
}
