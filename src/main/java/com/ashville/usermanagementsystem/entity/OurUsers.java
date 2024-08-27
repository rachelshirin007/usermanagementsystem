package com.ashville.usermanagementsystem.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ourusers")
@Data
@EntityScan(basePackages = "com.ashville.usermanagementsystem.entity")
public class OurUsers implements UserDetails {
    
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

   @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    public String getUsername() {
        return email;
    }

    // Default constructor
    public OurUsers() {}

    // Constructor with fields
    public OurUsers(Integer userId, String email, String password, String name, String mobile, String city, String role, Department department) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.mobile = mobile;
        this.city = city;
        this.role = role;
        this.department = department;
    }

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
