package com.ashville.usermanagementsystem.DTO;

import java.time.LocalDateTime;
import java.util.List;

import com.ashville.usermanagementsystem.entity.CheckIn;
import com.ashville.usermanagementsystem.entity.Department;
import com.ashville.usermanagementsystem.entity.OurUsers;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestResponse {

    private int statusCode;
    private String error;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String name;
    private String city;
    private String role;
    private String email;
    private String password;
    private String mobile;
    private OurUsers ourUsers;
    private String deptName;
    private Department department;
    private LocalDateTime checkinTime;
    private LocalDateTime checkoutTime; 
    private CheckIn checkIn;
    private List<CheckIn> checkInList;
    private List<OurUsers> ourUsersList;
    private List<Department> departmentList;
    
}
