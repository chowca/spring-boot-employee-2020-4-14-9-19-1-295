package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.controller.EmployeeController;
import com.thoughtworks.springbootemployee.model.Employee;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {
    @Autowired
    private EmployeeController employeeController;
    private List<Employee> testEmployees = new ArrayList<>();

    @Before
    public void setUp() {
        RestAssuredMockMvc.standaloneSetup(employeeController);
        testEmployees.add(new Employee(0, "Xiaoming", 20, "Male"));
        testEmployees.add(new Employee(1, "Xiaohong", 19, "Female"));
        testEmployees.add(new Employee(2, "Xiaozhi", 15, "Male"));
        testEmployees.add(new Employee(3, "Xiaogang", 16, "Male"));
        testEmployees.add(new Employee(4, "Xiaoxia", 15, "Female"));
    }

    @Test
    public void should_find_all_employees() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/employees");
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals(employeeController.getAllEmployees(null, null, null).getBody(), employees);
    }


    @Test
    public void should_find_employee_by_id() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/employees/1");
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        Employee employee = response.getBody().as(Employee.class);
        Assert.assertEquals(1, employee.getId());
    }

    @Test
    public void should_add_an_employee() {
        Employee newEmployee = new Employee(5, "Ben", 25, "Male");
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(newEmployee)
                .when()
                .post("/employees");
        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
        Employee employee = response.getBody().as(Employee.class);
        Assert.assertEquals(newEmployee, employee);
    }

    @Test
    public void should_update_an_employee() {
        Employee updatedEmployee = new Employee(0, "Ken", 35, "Male");
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(updatedEmployee)
                .when()
                .put("/employees/0");
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        Employee employee = response.getBody().as(Employee.class);
        Assert.assertEquals(updatedEmployee, employee);
    }

    @Test
    public void should_find_employees_by_gender() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .params("gender", "male")
                .when()
                .get("/employees");
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(3, employees.size());
        Assert.assertEquals(employeeController.getAllEmployees("Male", null, null).getBody(), employees);
    }

    @Test
    public void should_delete_an_employee() {
        Employee expectedEmployee = employeeController.get(0).getBody();
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .delete("/employees/0");
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        Employee deletedEmployee = response.getBody().as(Employee.class);
        Assert.assertEquals(4, employeeController.getAllEmployees(null, null, null).getBody().size());
        Assert.assertEquals(expectedEmployee, deletedEmployee);
    }

    @Test
    public void should_return_error_when_delete_a_not_exist_employee() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .delete("/employees/5");
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
        Assert.assertEquals(5, employeeController.getAllEmployees(null, null, null).getBody().size());
    }
}
