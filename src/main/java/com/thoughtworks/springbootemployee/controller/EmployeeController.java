package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private List<Employee> employees = new ArrayList<>();

    public EmployeeController() {
        employees.add(new Employee(0, "Xiaoming", 20, "Male"));
        employees.add(new Employee(1, "Xiaohong", 19, "Female"));
        employees.add(new Employee(2, "Xiaozhi", 15, "Male"));
        employees.add(new Employee(3, "Xiaogang", 16, "Male"));
        employees.add(new Employee(4, "Xiaoxia", 15, "Female"));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllEmployees(@RequestParam(value = "gender", required = false) String gender) {
        if (gender != null) {
            return employees
                    .stream()
                    .filter(employee -> employee.getGender().toLowerCase().equals(gender.toLowerCase()))
                    .collect(Collectors.toList());
        } else {
            return employees;
        }
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getEmployee(@PathVariable int employeeId) {
        Employee targetedEmployee = employees
                .stream()
                .filter(employee -> employee.getId() == employeeId)
                .findAny()
                .orElse(null);
        if (targetedEmployee != null) {
            return new ResponseEntity<>(targetedEmployee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createNewEmployee(@RequestBody Employee employee) {
        employees.add(employee);
        return employee;
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable int employeeId) {
        Employee targetedEmployee = employees
                .stream()
                .filter(employee -> employee.getId() == employeeId)
                .findAny()
                .orElse(null);
        if (targetedEmployee != null) {
            employees.remove(targetedEmployee);
            return new ResponseEntity<>(targetedEmployee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int employeeId, @RequestBody Employee updateEmployee) {
        Employee targetedEmployee = employees
                .stream()
                .filter(employee -> employee.getId() == employeeId)
                .findAny()
                .orElse(null);
        if (targetedEmployee != null) {
            employees.set(employees.indexOf(targetedEmployee), updateEmployee);
            return new ResponseEntity<>(updateEmployee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}