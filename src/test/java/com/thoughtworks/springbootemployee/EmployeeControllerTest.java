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
//import org.springframework.cloud.contract.spec.internal.HttpStatus;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {
    @Autowired
    private EmployeeController employeeController;

    @Before
    public void setUp(){
        RestAssuredMockMvc.standaloneSetup(employeeController);
    }

    @Test
    public void should_find_all_employees(){
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("employees");
        Assert.assertEquals(HttpStatus.OK.value(),response.getStatusCode());
        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals("Xiaoming",employees.get(0).getName());
        Assert.assertEquals("Xiaohong",employees.get(1).getName());
        Assert.assertEquals("Xiaozhi", employees.get(2).getName());
        Assert.assertEquals("Xiaogang",employees.get(3).getName());
        Assert.assertEquals("Xiaoxia", employees.get(4).getName());
    }

    @Test
    public void should_find_employee_by_id(){
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("employees/1");
        Assert.assertEquals(HttpStatus.OK.value(),response.getStatusCode());
        Employee employee = response.getBody().as(Employee.class);
        Assert.assertEquals(employee.getId(),1);
    }

    @Test
    public void should_find_employees_by_gender(){
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .params("gender","male")
                .when()
                .get("employees");
        Assert.assertEquals(HttpStatus.OK.value(),response.getStatusCode());
        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(3,employees.size());
        Assert.assertEquals("Xiaoming", employees.get(0).getName());
        Assert.assertEquals("Xiaozhi", employees.get(1).getName());
        Assert.assertEquals("Xiaogang", employees.get(2).getName());
    }
}
