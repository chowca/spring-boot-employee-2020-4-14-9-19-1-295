package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
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
public class EmployeeControllerTest {
    private List<Employee> testEmployees = new ArrayList<>();
    private List<Employee> testEmployeesByGender = new ArrayList<>();
    private List<Employee> testEmployeesWithPaging = new ArrayList<>();
    private EmployeeController employeeController;

    @Mock
    private EmployeeService service;

    @Before
    public void setUp() {
        employeeController = new EmployeeController(service);
        RestAssuredMockMvc.standaloneSetup(employeeController);

        testEmployees.add(new Employee(5, "Xiaoming", 20, "male", 10000, 2, null));
        testEmployees.add(new Employee(6, "Xiaohong", 19, "female", 10000, 2, null));
        testEmployees.add(new Employee(7, "Xiaozhi", 30, "male", 10000, 2, null));
        testEmployees.add(new Employee(8, "Xiaogang", 36, "male", 10000, 2, null));
        testEmployees.add(new Employee(9, "Xiaoxia", 45, "female", 10000, 2, null));

        testEmployeesByGender.add(new Employee(5, "Xiaoming", 20, "male", 10000, 2, null));
        testEmployeesByGender.add(new Employee(7, "Xiaozhi", 30, "male", 10000, 2, null));
        testEmployeesByGender.add(new Employee(8, "Xiaogang", 36, "male", 10000, 2, null));

        testEmployeesWithPaging.add(new Employee(5, "Xiaoming", 20, "male", 10000, 2, null));
        testEmployeesWithPaging.add(new Employee(6, "Xiaohong", 19, "female", 10000, 2, null));
    }

    @Test
    public void should_find_all_employees() {
        doReturn(testEmployees).when(service).getAll(any(), any(), any());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/employees");
        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        Assert.assertEquals(testEmployees, employees);
    }


    @Test
    public void should_find_employee_by_id() {
        doReturn(testEmployees.get(1)).when(service).getEmployeeById(any());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/employees/1");
        Employee employee = response.getBody().as(Employee.class);

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        Assert.assertEquals(testEmployees.get(1), employee);
    }

    @Test
    public void should_add_an_employee() {
        Employee newEmployee = new Employee(5, "Ben", 25, "Male", 2000, 1, null);
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
        Employee updatedEmployee = new Employee(0, "Ken", 35, "Male", 2000, 1, null);
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

}
