package com.tohir.springboot.repository;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.tohir.springboot.entity.Employee;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @DisplayName("JUnit test for save employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {

        // given - precondition or setup
        Employee employee = Employee.builder().firstName("Tohir").lastName("Raihan").email("tohir.raihan@gmail.com")
                .build();

        // when - action or the behaviour that we are going to test
        Employee savedEmployee = employeeRepository.save(employee);

        // then - verify the output
        Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @DisplayName("JUnit test for find all employee operation")
    @Test
    public void givenEmployeeList_whenFindAll_thenReturnEmployeeList() {

        // given - precondition or setup
        Employee employee1 = Employee.builder().firstName("Tohir").lastName("Raihan").email("tohir.raihan@gmail.com")
                .build();
        Employee employee2 = Employee.builder().firstName("Test").lastName("Name").email("test.name@gmail.com")
                .build();

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        // when - action or the behaviout that we are going to test
        List<Employee> employees = employeeRepository.findAll();

        // then - verify the output
        Assertions.assertThat(employees).isNotNull();
        Assertions.assertThat(employees.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for find employee by id operation")
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {

        // given - precondition or setup
        Employee employee = Employee.builder().firstName("Tohir").lastName("Raihan").email("tohir.raihan@gmail.com")
                .build();
        employeeRepository.save(employee);

        // when - action or the behaviour that we are going to test
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();

        // then - verify the output
        Assertions.assertThat(employeeDB).isNotNull();
    }

    @DisplayName("JUnit test for get employee by email operation")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {

        // given - precondition or setup
        Employee employee = Employee.builder().firstName("Tohir").lastName("Raihan").email("tohir.raihan@gmail.com")
                .build();
        employeeRepository.save(employee);

        // when - action or the behaviour that we are going to test
        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();

        // then - verify the output
        Assertions.assertThat(employeeDB).isNotNull();
    }

    @DisplayName("JUnit test for update employee operation")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {

        // given - precondition or setup
        Employee employee = Employee.builder().firstName("Tohir").lastName("Raihan").email("tohir.raihan@gmail.com")
                .build();
        employeeRepository.save(employee);

        // when - action or the behaviour that we are going to test
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();
        employeeDB.setEmail("test@gmail.com");

        Employee updatedEmployee = employeeRepository.save(employeeDB);

        // then - verify the output
        Assertions.assertThat(updatedEmployee.getEmail()).isEqualTo("test@gmail.com");
    }

    @DisplayName("JUnit test for delete employee operation")
    @Test
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee() {

        // given - precondition or setup
        Employee employee = Employee.builder().firstName("Tohir").lastName("Raihan").email("tohir.raihan@gmail.com")
                .build();
        employeeRepository.save(employee);

        // when - action or the behaviour that we are going to test
        employeeRepository.delete(employee);
        Optional<Employee> optionalEmployee = employeeRepository.findById(employee.getId());

        // then - verify the output
        Assertions.assertThat(optionalEmployee).isEmpty();
    }

    @DisplayName("JUnit test for custom query using JPQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployee() {

        // given - precondition or setup
        Employee employee = Employee.builder().firstName("Tohir").lastName("Raihan").email("tohir.raihan@gmail.com")
                .build();

        employeeRepository.save(employee);

        String firstName = "Tohir";
        String lastName = "Raihan";

        // when - action or the behaviour that we are going to test
        Employee employeeDB = employeeRepository.findByJPQL(firstName, lastName);

        // then - verify the output
        Assertions.assertThat(employeeDB).isNotNull();
    }

}
