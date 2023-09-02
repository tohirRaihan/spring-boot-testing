package com.tohir.springboot.service;

import java.util.List;

import com.tohir.springboot.entity.Employee;

public interface EmployeeService {
    
    Employee saveEmployee(Employee employee);

    List<Employee> getAllEmployees();
    
}
