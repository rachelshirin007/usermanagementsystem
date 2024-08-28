package com.ashville.usermanagementsystem.DTO;
import java.util.List;

import com.ashville.usermanagementsystem.entity.CheckIn;
import com.ashville.usermanagementsystem.entity.Department;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer userId;
    private String name;
    private String city;
    private String role;
    private String email;
    private String password;
    private String mobile;

    private Department department;
    private CheckIn checkIn;
    private List<CheckIn> checkInList;
    private List<Department> departmentList;
}
