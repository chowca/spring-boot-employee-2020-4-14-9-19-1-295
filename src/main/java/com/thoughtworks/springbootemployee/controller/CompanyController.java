package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private List<Company> companies = new ArrayList<>();
    private List<Employee> alibabaEmployees = new ArrayList<>();
    private List<Employee> ooclEmployees = new ArrayList<>();

    public CompanyController() {
        alibabaEmployees.add(new Employee(4, "alibaba1", 20, "male", 6000));
        alibabaEmployees.add(new Employee(11, "tengxun2", 19, "female", 7000));
        alibabaEmployees.add(new Employee(6, "alibaba3", 19, "male", 8000));
        alibabaEmployees.add(new Employee(13, "huiwei1", 16, "male", 9000));
        companies.add(new Company("alibaba", 1, 200, alibabaEmployees));

        ooclEmployees.add(new Employee(0, "Xiaoming", 20, "Male", 10000));
        ooclEmployees.add(new Employee(1, "Xiaohong", 19, "Female", 10000));
        ooclEmployees.add(new Employee(2, "Xiaozhi", 15, "Male", 10000));
        ooclEmployees.add(new Employee(3, "Xiaogang", 16, "Male", 10000));
        ooclEmployees.add(new Employee(4, "Xiaoxia", 15, "Female", 10000));
        companies.add(new Company("oocl", 2, 250, ooclEmployees));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Company> getAllCompanies() {
        return companies;
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<Company> getCompany(@PathVariable int companyId) {
        Company targetedCompany = companies
                .stream()
                .filter(company -> company.getCompanyId() == companyId)
                .findAny()
                .orElse(null);
        if (targetedCompany != null) {
            return new ResponseEntity<>(targetedCompany, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{companyId}/employees")
    public ResponseEntity<List<Employee>> getCompanyEmployees(@PathVariable int companyId) {
        Company targetedCompany = companies
                .stream()
                .filter(company -> company.getCompanyId() == companyId)
                .findAny()
                .orElse(null);
        if (targetedCompany != null) {
            return new ResponseEntity<>(targetedCompany.getEmployees(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company createNewCompany(@RequestBody Company company) {
        companies.add(company);
        return company;
    }

    @DeleteMapping("/{companyId}/employees")
    public ResponseEntity<List<Employee>> deleteCompanyEmployees(@PathVariable int companyId) {
        Company targetedCompany = companies
                .stream()
                .filter(company -> company.getCompanyId() == companyId)
                .findAny()
                .orElse(null);
        if (targetedCompany != null) {
            targetedCompany.setEmployees(null);
            return new ResponseEntity<>(targetedCompany.getEmployees(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<Company> updateEmployee(@PathVariable int companyId, @RequestBody Company updateCompany) {
        Company targetedCompany = companies
                .stream()
                .filter(company -> company.getCompanyId() == companyId)
                .findAny()
                .orElse(null);
        if (targetedCompany != null) {
            companies.set(companies.indexOf(targetedCompany), updateCompany);
            return new ResponseEntity<>(updateCompany, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
