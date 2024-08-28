package com.ashville.usermanagementsystem.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ourusers")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityScan(basePackages = "com.ashville.usermanagementsystem.entity")
public class OurUsers  {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    @Column(unique = true, nullable  = false)
    private String email;
    @Column(nullable  = false)
    private String name;
    @Column(nullable  = false)
    private String password;
    @Column(unique = true, nullable  = false)
    private String mobile;
    private String city;
    private String role;
    // Mapping dept_id as a separate field but not for insert/update
    //@Column(name = "dept_id", insertable = false, updatable = false)
    //private Integer deptId;

    @ManyToOne
    @JoinColumn(name = "dept_id", referencedColumnName = "departmentId")
    private Department department;



    // Default constructor


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }


}
