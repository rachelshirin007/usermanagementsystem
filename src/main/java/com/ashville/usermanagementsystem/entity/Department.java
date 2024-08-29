package com.ashville.usermanagementsystem.entity;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer departmentId;

    @Column(nullable = false, unique = true)
    private String deptName;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OurUsers> users;

    // Constructors
    public Department() {}

    public Department(String deptName) {
        this.deptName = deptName;
    }

    // Getters and Setters
    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public List<OurUsers> getUsers() {
        return users;
    }

    public void setUsers(List<OurUsers> users) {
        this.users = users;
    }
}
