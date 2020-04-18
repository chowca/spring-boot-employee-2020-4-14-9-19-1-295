package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getAll(Integer page, Integer pageSize) {
        if ((page != null) && (pageSize != null)) {
            return companyRepository.findAll(PageRequest.of(page, pageSize)).getContent();
        } else {
            return companyRepository.findAll();
        }
    }

    public Company getCompanyById(Integer companyId) {
        return companyRepository.findById(companyId).orElse(null);
    }

    public Company createNewCompany(Company company) {
        return companyRepository.save(company);
    }

    public List<Employee> getEmployeesByCompanyId(Integer companyId) {
        Company targetedCompany = companyRepository.findById(companyId).orElse(null);
        if (targetedCompany == null) {
            return null;
        } else {
            return targetedCompany.getEmployees();
        }
    }

    public Company updateCompany(Integer companyId, Company updateCompany) {
        Company existedCompany = companyRepository.findById(companyId).orElse(null);
        if (existedCompany == null) {
            return null;
        } else {
            existedCompany.setCompanyName(updateCompany.getCompanyName());
            existedCompany.setEmployeesNumber(updateCompany.getEmployeesNumber());
            return companyRepository.save(existedCompany);
        }
    }

    public List<Employee> deleteEmployeesByCompanyId(Integer companyId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company == null) {
            return null;
        } else {
            companyRepository.deleteEmployeesById(companyId);
            return company.getEmployees();
        }
    }
}
