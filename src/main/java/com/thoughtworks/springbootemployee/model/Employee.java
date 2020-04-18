package com.thoughtworks.springbootemployee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeId;
    private String name;
    private Integer age;
    private String gender;
    private Integer salary;
    private Integer companyId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeId", referencedColumnName = "employeeId")
    private ParkingBoy parkingBoy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(employeeId, employee.employeeId) &&
                Objects.equals(name, employee.name) &&
                Objects.equals(age, employee.age) &&
                Objects.equals(gender, employee.gender) &&
                Objects.equals(salary, employee.salary) &&
                Objects.equals(companyId, employee.companyId) &&
                Objects.equals(parkingBoy, employee.parkingBoy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, name, age, gender, salary, companyId, parkingBoy);
    }
}
