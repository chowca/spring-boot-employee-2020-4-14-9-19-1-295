package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyControllerTest {
    private List<Company> testCompanies = new ArrayList<>();
    private List<Employee> testCompany1Employees = new ArrayList<>();
    private List<Employee> testCompany2Employees = new ArrayList<>();
    private List<Company> testCompaniesWithPaging = new ArrayList<>();
    private CompanyController companyController;

    @Mock
    private CompanyService service;

    @Before
    public void setUp() {
        companyController = new CompanyController(service);
        RestAssuredMockMvc.standaloneSetup(companyController);

        testCompany1Employees.add(new Employee(1, "alibaba1", 20, "male", 6000, 1, null));
        testCompany1Employees.add(new Employee(2, "tengxun2", 19, "female", 7000, 1, null));
        testCompany1Employees.add(new Employee(3, "alibaba3", 19, "male", 8000, 1, null));
        testCompany1Employees.add(new Employee(4, "huiwei1", 16, "male", 9000, 1, null));
        testCompanies.add(new Company(1, "alibaba", 200, testCompany1Employees));

        testCompany2Employees.add(new Employee(5, "Xiaoming", 20, "male", 10000, 2, null));
        testCompany2Employees.add(new Employee(6, "Xiaohong", 19, "female", 10000, 2, null));
        testCompany2Employees.add(new Employee(7, "Xiaozhi", 30, "male", 10000, 2, null));
        testCompany2Employees.add(new Employee(8, "Xiaogang", 36, "male", 10000, 2, null));
        testCompany2Employees.add(new Employee(9, "Xiaoxia", 45, "female", 10000, 2, null));
        testCompanies.add(new Company(2, "oocl", 250, testCompany2Employees));

        testCompaniesWithPaging.add(new Company(1, "alibaba", 200, testCompany1Employees));
    }

    @Test
    public void should_find_all_companies() {
        doReturn(testCompanies).when(service).getAll(any(), any());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/companies");
        List<Company> companies = response.getBody().as(new TypeRef<List<Company>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        Assert.assertEquals(testCompanies, companies);
    }

    @Test
    public void should_find_company_by_id() {
        doReturn(testCompanies.get(1)).when(service).getCompanyById(any());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/companies/1");
        Company company = response.getBody().as(Company.class);

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        Assert.assertEquals(testCompanies.get(1), company);
    }

    @Test
    public void should_find_employees_in_specific_company_by_id() {
        doReturn(testCompanies.get(1).getEmployees()).when(service).getEmployeesByCompanyId(any());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/companies/1/employees");
        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        Assert.assertEquals(testCompanies.get(1).getEmployees(), employees);
    }

    @Test
    public void should_return_1_company_in_page_0() {
        doReturn(testCompaniesWithPaging).when(service).getAll(any(), any());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .params("page", "0", "pageSize", "1")
                .when()
                .get("/companies");
        List<Company> companies = response.getBody().as(new TypeRef<List<Company>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        Assert.assertEquals(testCompaniesWithPaging, companies);
    }

    @Test
    public void should_add_a_company() {
        Company newCompany = new Company(3, "hello", 500, Arrays.asList(new Employee(1, "Testing", 20, "Male", 12000, 3, null)));
        doReturn(newCompany).when(service).createNewCompany(any());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(newCompany)
                .when()
                .post("/companies");
        Company company = response.getBody().as(Company.class);

        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
        Assert.assertEquals(newCompany, company);
    }


    @Test
    public void should_update_a_company() {
        Company updatedCompany = new Company(1, "hello", 500, Arrays.asList(new Employee(1, "Testing", 20, "Male", 12000, 1, null)));
        doReturn(updatedCompany).when(service).updateCompany(any(), any());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(updatedCompany)
                .when()
                .put("/companies/1");
        Company company = response.getBody().as(Company.class);

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        Assert.assertEquals(updatedCompany, company);
    }

    @Test
    public void should_delete_employees_in_a_company() {
        List<Employee> expectedEmployees = testCompanies.get(0).getEmployees();
        doReturn(expectedEmployees).when(service).deleteEmployeesByCompanyId(any());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .delete("/companies/1");
        List<Employee> deletedEmployee = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        Assert.assertEquals(expectedEmployees, deletedEmployee);
    }
}
