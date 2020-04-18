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
    private EmployeeRepository employeeRepository;

    public List<Employee> getAll(String gender, Integer page, Integer pageSize) {
        if (gender != null) {
            return employeeRepository.findAllByGender(gender);
        } else if ((page != null) && (pageSize != null)) {
            return employeeRepository.findAll(PageRequest.of(page, pageSize)).getContent();
        } else {
            return employeeRepository.findAll();
        }
    }

    public Employee getEmployeeById(Integer employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    public Employee createNewEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Integer employeeId, Employee updateEmployee) {
        Employee existedEmployee = employeeRepository.findById(employeeId).orElse(null);
        if (existedEmployee == null) {
            return null;
        } else {
            existedEmployee.setName(updateEmployee.getName());
            existedEmployee.setAge(updateEmployee.getAge());
            existedEmployee.setGender(updateEmployee.getGender());
            existedEmployee.setSalary(updateEmployee.getSalary());
            existedEmployee.setCompanyId(updateEmployee.getCompanyId());
            return employeeRepository.save(existedEmployee);
        }
    }

    public Employee deleteEmployeeById(Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) {
            return null;
        } else {
            employeeRepository.delete(employee);
            return employee;
        }
    }
}
