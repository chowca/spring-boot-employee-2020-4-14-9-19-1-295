package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService service;

    public EmployeeController() {
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees(@RequestParam(value = "gender", required = false) String gender,
                                                          @RequestParam(value = "page", required = false) Integer page,
                                                          @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return new ResponseEntity<>(service.getAll(gender, page, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> get(@PathVariable int employeeId) {
        Employee targetedEmployee = service.getEmployeeById(employeeId);
        if (targetedEmployee != null) {
            return new ResponseEntity<>(targetedEmployee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        return new ResponseEntity<>(service.createNewEmployee(employee), HttpStatus.CREATED);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int employeeId, @RequestBody Employee updateEmployee) {
        Employee updatedEmployee = service.updateEmployee(employeeId, updateEmployee);
        if (updatedEmployee != null) {
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    /*
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllEmployees(@RequestParam(value = "gender", required = false) String gender,
                                          @RequestParam(value = "page", required = false) Integer page,
                                          @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (gender != null) {
            return employees
                    .stream()
                    .filter(employee -> employee.getGender().toLowerCase().equals(gender.toLowerCase()))
                    .collect(Collectors.toList());
        } else if ((page != null) && (pageSize != null)) {
            return employees
                    .stream()
                    .skip(page * pageSize)
                    .limit(pageSize)
                    .collect(Collectors.toList());
        } else {
            return employees;
        }
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

     */
}