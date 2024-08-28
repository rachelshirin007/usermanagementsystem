package com.ashville.usermanagementsystem.DTO;

import java.util.List;

import com.ashville.usermanagementsystem.entity.Department;
import com.ashville.usermanagementsystem.entity.OurUsers;
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
public class DepartmentDTO {
    private Integer departmentId;
    private String deptName;
    private Department department;
    private List<OurUsers> ourUsersList;
    private OurUsers ourUsers;
}
