package com.tohir.springboot.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tohir.springboot.entity.Employee;
import com.tohir.springboot.exception.ResourceNotFoundException;
import com.tohir.springboot.repository.EmployeeRepository;
import com.tohir.springboot.service.impl.EmployeeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setUp() {
        // employeeRepository = Mockito.mock(EmployeeRepository.class);
        // employeeService = new EmployeeServiceImpl(employeeRepository);

        this.employee = Employee.builder().id(1L).firstName("Tohir").lastName("Raihan")
                .email("tohir.raihan@gmail.com")
                .build();
    }

    @DisplayName("JUnit test for saveEmployee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {

        // given - precondition or setup
        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

        // when - action or the behaviour that we are going to test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // then - verify the output
        Assertions.assertThat(savedEmployee);
    }

    @DisplayName("JUnit test for saveEmployee method which throws exception")
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException() {

        // given - precondition or setup
        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));
        // BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

        // when - action or the behaviour that we are going to test
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.saveEmployee(employee);
        });

        // then - verify the output
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @DisplayName("JUnit test for getAllEmployees method")
    @Test
    public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList() {

        // given - precondition or setup
        Employee employee2 = Employee.builder().id(2L).firstName("Jack").lastName("Harpoor")
                .email("jack@gmail.com")
                .build();

        BDDMockito.given(employeeRepository.findAll()).willReturn(List.of(employee, employee2));

        // when - action or the behaviour that we are going to test
        List<Employee> employees = employeeService.getAllEmployees();

        // then - verify the output
        Assertions.assertThat(employees).isNotNull();
        Assertions.assertThat(employees.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for getAllEmployees method (negative senario)")
    @Test
    public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeesList() {

        // given - precondition or setup
        BDDMockito.given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        // when - action or the behaviour that we are going to test
        List<Employee> employees = employeeService.getAllEmployees();

        // then - verify the output
        Assertions.assertThat(employees).isEmpty();
        Assertions.assertThat(employees.size()).isEqualTo(0);
    }

    @DisplayName("JUnit test for getEmployeeById method")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
        // given - precondition or setup
        BDDMockito.given(employeeRepository.findById(employee.getId())).willReturn(Optional.of(employee));

        // when - action or the behaviour that we are going to test
        Employee employeeDB = employeeService.getEmployeeById(employee.getId()).get();

        // then - verify the output
        Assertions.assertThat(employeeDB).isNotNull();
    }
}
