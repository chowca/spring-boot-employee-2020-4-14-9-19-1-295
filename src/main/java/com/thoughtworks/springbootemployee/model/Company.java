package com.thoughtworks.springbootemployee.model;

import java.util.List;
import java.util.Objects;

public class Company {
    private String companyName;
    private int companyId;
    private int employeesNumber;
    private List<Employee> employees;

    public Company(){}

    public Company(String companyName, int companyId, int employeesNumber, List<Employee> employees) {
        this.companyName = companyName;
        this.companyId = companyId;
        this.employeesNumber = employeesNumber;
        this.employees = employees;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getEmployeesNumber() {
        return employeesNumber;
    }

    public void setEmployeesNumber(int employeesNumber) {
        this.employeesNumber = employeesNumber;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return companyId == company.companyId &&
                employeesNumber == company.employeesNumber &&
                Objects.equals(companyName, company.companyName) &&
                Objects.equals(employees, company.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyName, companyId, employeesNumber, employees);
    }
}
