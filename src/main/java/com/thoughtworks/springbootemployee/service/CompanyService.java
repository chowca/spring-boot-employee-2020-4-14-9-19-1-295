package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
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

}
