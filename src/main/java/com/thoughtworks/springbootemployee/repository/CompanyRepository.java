package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Employee E WHERE E.companyId = :companyId")
    void deleteEmployeesById(@Param("companyId") Integer companyId);
}
