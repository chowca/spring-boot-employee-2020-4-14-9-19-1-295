package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService service;

    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies(@RequestParam(value = "page", required = false) Integer page,
                                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return new ResponseEntity<>(service.getAll(page, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<Company> get(@PathVariable Integer companyId) {
        Company targetedCompany = service.getCompanyById(companyId);
        if (targetedCompany != null) {
            return new ResponseEntity<>(targetedCompany, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{companyId}/employees")
    public ResponseEntity<List<Employee>> getCompanyEmployees(@PathVariable Integer companyId) {
        List<Employee> targetedEmployees = service.getEmployeesByCompanyId(companyId);
        if (targetedEmployees != null) {
            return new ResponseEntity<>(targetedEmployees, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    /*
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
    }*/
}
