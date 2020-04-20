package com.thoughtworks.springbootemployee.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "parking_boy")
public class ParkingBoy {
    @Id
    @Column(name = "employee_id")
    private Integer employeeId;
    @Column(name = "nick_name")
    private String nickName;

    @JsonIgnore
    @OneToOne(mappedBy = "parkingBoy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Employee employee;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingBoy that = (ParkingBoy) o;
        return Objects.equals(employeeId, that.employeeId) &&
                Objects.equals(nickName, that.nickName) &&
                Objects.equals(employee, that.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, nickName, employee);
    }
}