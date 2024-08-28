package com.ashville.usermanagementsystem.services;


import org.springframework.stereotype.Service;

import com.ashville.usermanagementsystem.DTO.DepartmentDTO;
import com.ashville.usermanagementsystem.DTO.RequestResponse;

@Service
public interface DepartementsServ {

    public DepartmentDTO registerDept(DepartmentDTO registrationRequest);
    public DepartmentDTO getAllDepts();
    public RequestResponse updateDept(Integer deptId, DepartmentDTO deptRequest);
    public RequestResponse deleteDepartment(Integer deptId) ;
    public RequestResponse getDepartmentById(Integer deptId);

}





    