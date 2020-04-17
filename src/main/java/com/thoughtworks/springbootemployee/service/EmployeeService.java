package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository repository;

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public Employee getEmployeeById(Integer employeeId) {
        return repository.findById(employeeId).orElse(null);
    }

    public Employee createNewEmployee(Employee employee) {
        return repository.save(employee);
    }

    public Employee updateEmployee(Integer employeeId, Employee updateEmployee) {
        Employee existedEmployee = repository.findById(employeeId).orElse(null);
        if (existedEmployee == null) {
            return null;
        } else {
            existedEmployee.setName(updateEmployee.getName());
            existedEmployee.setAge(updateEmployee.getAge());
            existedEmployee.setGender(updateEmployee.getGender());
            existedEmployee.setSalary(updateEmployee.getSalary());
            return repository.save(existedEmployee);
        }
    }

    public Employee deleteEmployeeById(Integer employeeId) {
        Employee employee = repository.findById(employeeId).orElse(null);
        if (employee == null) {
            return null;
        } else {
            repository.delete(employee);
            return employee;
        }
    }

    public List<Employee> getAll(String gender, Integer page, Integer pageSize) {
        if (gender != null) {
            return repository.findAllByGender(gender);
        } else if ((page != null) && (pageSize != null)) {
            return repository.findAll(PageRequest.of(page, pageSize)).getContent();
        } else {
            return repository.findAll();
        }
    }
}
