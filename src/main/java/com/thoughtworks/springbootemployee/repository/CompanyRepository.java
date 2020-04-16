package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {
    private List<Company> companies = new ArrayList<>();
    private List<Employee> alibabaEmployees = new ArrayList<>();
    private List<Employee> ooclEmployees = new ArrayList<>();

    public CompanyRepository() {
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

    public List<Company> findAll() {
        return companies;
    }

    public Company findById(Integer companyId) {
        return companies
                .stream()
                .filter(company -> company.getCompanyId() == companyId)
                .findAny()
                .orElse(null);
    }

    public List<Employee> findEmployeesById(Integer companyId) {
        return companies
                .stream()
                .filter(company -> company.getCompanyId() == companyId)
                .findAny()
                .orElse(null)
                .getEmployees();
    }

    public Company save(Company company) {
        companies.add(company);
        return company;
    }

    public void update(Company existedCompany, Company updateCompany) {
        companies.get(companies.indexOf(existedCompany)).setCompanyName(updateCompany.getCompanyName());
        companies.get(companies.indexOf(existedCompany)).setEmployeesNumber(updateCompany.getEmployeesNumber());
        companies.get(companies.indexOf(existedCompany)).setEmployees(updateCompany.getEmployees());
    }

    public List<Employee> deleteEmployees(Company company) {
        companies.get(companies.indexOf(company)).setEmployees(null);
        return company.getEmployees();
    }
}
