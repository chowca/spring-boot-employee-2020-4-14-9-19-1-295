package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {
    private List<Employee> employees = new ArrayList<>();

    public EmployeeRepository() {
        employees.add(new Employee(0, "Xiaoming", 20, "Male"));
        employees.add(new Employee(1, "Xiaohong", 19, "Female"));
        employees.add(new Employee(2, "Xiaozhi", 15, "Male"));
        employees.add(new Employee(3, "Xiaogang", 16, "Male"));
        employees.add(new Employee(4, "Xiaoxia", 15, "Female"));
    }

    public List<Employee> findAll() {
        return employees;
    }

    public Employee findById(Integer employeeId) {
        return employees
                .stream()
                .filter(employee -> employee.getId() == employeeId)
                .findFirst()
                .orElse(null);
    }

    public Employee save(Employee employee) {
        employees.add(employee);
        return employee;
    }

    public void update(Employee existedEmployee, Employee updateEmployee) {
        employees.get(employees.indexOf(existedEmployee)).setName(updateEmployee.getName());
        employees.get(employees.indexOf(existedEmployee)).setAge(updateEmployee.getAge());
        employees.get(employees.indexOf(existedEmployee)).setGender(updateEmployee.getGender());
        employees.get(employees.indexOf(existedEmployee)).setSalary(updateEmployee.getSalary());
    }

    public Employee delete(Employee employee) {
        employees.remove(employee);
        return employee;
    }
}
