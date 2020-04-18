package com.thoughtworks.springbootemployee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyId;
    private String companyName;
    private Integer employeesNumber;

    @OneToMany(targetEntity = Employee.class, mappedBy = "companyId", fetch = FetchType.EAGER, orphanRemoval=true)
    private List<Employee> employees;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(companyId, company.companyId) &&
                Objects.equals(companyName, company.companyName) &&
                Objects.equals(employeesNumber, company.employeesNumber) &&
                Objects.equals(employees, company.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, companyName, employeesNumber, employees);
    }
}
