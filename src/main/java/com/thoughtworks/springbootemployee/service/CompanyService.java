package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository repository;

    public List<Company> getAll(Integer page, Integer pageSize) {
        if ((page != null) && (pageSize != null)) {
            return repository.findAll()
                    .stream()
                    .skip(page * pageSize)
                    .limit(pageSize)
                    .collect(Collectors.toList());
        } else {
            return repository.findAll();
        }
    }

    public Company getCompanyById(Integer companyId) {
        return repository.findById(companyId);
    }

    public List<Employee> getEmployeesByCompanyId(Integer companyId) {
        return repository.findEmployeesById(companyId);
    }

    public Company createNewCompany(Company company) {
        return repository.save(company);
    }

    public Company updateCompany(Integer companyId, Company updateCompany) {
        Company existedCompany = repository.findById(companyId);
        if ((existedCompany != null) && (existedCompany.getCompanyId() == updateCompany.getCompanyId())) {
            repository.update(existedCompany, updateCompany);
            return repository.findById(companyId);
        } else {
            return null;
        }
    }
}
