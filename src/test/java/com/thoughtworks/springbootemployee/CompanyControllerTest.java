package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.controller.CompanyController;
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

        testCompany1Employees.add(new Employee(4, "alibaba1", 20, "male", 6000));
        testCompany1Employees.add(new Employee(11, "tengxun2", 19, "female", 7000));
        testCompany1Employees.add(new Employee(6, "alibaba3", 19, "male", 8000));
        testCompany1Employees.add(new Employee(13, "huiwei1", 16, "male", 9000));
        testCompanies.add(new Company("alibaba", 1, 200, testCompany1Employees));

        testCompany2Employees.add(new Employee(0, "Xiaoming", 20, "Male", 10000));
        testCompany2Employees.add(new Employee(1, "Xiaohong", 19, "Female", 10000));
        testCompany2Employees.add(new Employee(2, "Xiaozhi", 15, "Male", 10000));
        testCompany2Employees.add(new Employee(3, "Xiaogang", 16, "Male", 10000));
        testCompany2Employees.add(new Employee(4, "Xiaoxia", 15, "Female", 10000));
        testCompanies.add(new Company("oocl", 2, 250, testCompany2Employees));

        testCompaniesWithPaging.add(new Company("alibaba", 1, 200, testCompany1Employees));
    }

    @Test
    public void should_find_all_companies() {
        doReturn(testCompanies).when(service).getAll(any(),any());
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

    /*
    @Test
    public void should_add_an_employee() {
        Employee newEmployee = new Employee(5, "Ben", 25, "Male");
        doReturn(newEmployee).when(service).createNewEmployee(any());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(newEmployee)
                .when()
                .post("/employees");
        Employee employee = response.getBody().as(Employee.class);

        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
        Assert.assertEquals(newEmployee, employee);
    }

    @Test
    public void should_update_an_employee() {
        Employee updatedEmployee = new Employee(0, "Ken", 35, "Male");
        doReturn(updatedEmployee).when(service).updateEmployee(any(), any());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(updatedEmployee)
                .when()
                .put("/employees/0");
        Employee employee = response.getBody().as(Employee.class);

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        Assert.assertEquals(updatedEmployee, employee);
    }

    @Test
    public void should_find_employees_by_gender() {
        doReturn(testEmployeesByGender).when(service).getAll(any(), any(), any());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .params("gender", "male")
                .when()
                .get("/employees");
        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        Assert.assertEquals(testEmployeesByGender, employees);
    }

    @Test
    public void should_delete_an_employee() {
        Employee expectedEmployee = testEmployees.get(0);
        doReturn(expectedEmployee).when(service).deleteEmployeeById(any());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .delete("/employees/0");
        Employee deletedEmployee = response.getBody().as(Employee.class);

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        Assert.assertEquals(expectedEmployee, deletedEmployee);
    }

    @Test
    public void should_return_error_when_delete_a_not_exist_employee() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .delete("/employees/5");

        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    @Test
    public void should_return_2_employee_in_page_0() {
        doReturn(testEmployeesWithPaging).when(service).getAll(any(), any(), any());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .params("page", "0", "pageSize", "2")
                .when()
                .get("/employees");
        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        Assert.assertEquals(testEmployeesWithPaging, employees);
    }
    */
}