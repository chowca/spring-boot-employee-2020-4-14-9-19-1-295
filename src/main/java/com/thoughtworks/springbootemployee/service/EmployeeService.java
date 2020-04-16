package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository repository;

    public List<Employee> getAll(String gender, Integer page, Integer pageSize) {
        if (gender != null) {
            return repository.findAll()
                    .stream()
                    .filter(employee -> employee.getGender().toLowerCase().equals(gender.toLowerCase()))
                    .collect(Collectors.toList());
        } else if ((page != null) && (pageSize != null)) {
            return repository.findAll()
                    .stream()
                    .skip(page * pageSize)
                    .limit(pageSize)
                    .collect(Collectors.toList());
        } else {
            return repository.findAll();
        }
    }

    public Employee getEmployeeById(int employeeId) {
        return repository.findById(employeeId);
    }

    public Employee createNewEmployee(Employee employee) {
        return repository.save(employee);
    }

    public Employee updateEmployee(int employeeId, Employee updateEmployee) {
        Employee existedEmployee = repository.findById(employeeId);
        if ((existedEmployee != null) && (existedEmployee.getId() == updateEmployee.getId())) {
            repository.update(existedEmployee, updateEmployee);
            return repository.findById(employeeId);
        } else {
            return null;
        }
    }

    public Employee deleteEmployeeById(int employeeId) {
        Employee employee = repository.findById(employeeId);
        if (employee != null) {
            return repository.delete(employee);
        } else {
            return null;
        }
    }
}
