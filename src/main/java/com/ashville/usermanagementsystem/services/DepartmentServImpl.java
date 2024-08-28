package com.ashville.usermanagementsystem.services;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.ashville.usermanagementsystem.DTO.DepartmentDTO;
import com.ashville.usermanagementsystem.DTO.RequestResponse;
import com.ashville.usermanagementsystem.entity.Department;
import com.ashville.usermanagementsystem.mapper.entityDTOmapper;
import com.ashville.usermanagementsystem.repository.DepartmentRepository;

@Service
public class DepartmentServImpl {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private entityDTOmapper entitydtomapper;

    public RequestResponse registerDept(DepartmentDTO registrationRequest){
        Department dept = new Department();
        dept.setDeptName(registrationRequest.getDeptName());
        departmentRepository.save(dept);
        return RequestResponse.builder()
                .statusCode(200)
                .message("Department created successfully")
                .build();
    }

    public RequestResponse getAllDepts() {
    List<Department> depts = departmentRepository.findAll();
        List<DepartmentDTO> deptDtoList = depts.stream()
                .map(entitydtomapper::mapDeptToBasic)
                .collect(Collectors.toList());

        return  RequestResponse.builder()
                .statusCode(200)
                .departmentList(deptDtoList)
                .build();
    
    }

    public RequestResponse updateDept(Integer deptId, DepartmentDTO deptRequest) throws NotFoundException {
        Department dept = departmentRepository.findById(deptId).orElseThrow(()-> new NotFoundException());
        dept.setDeptName(deptRequest.getDeptName());
        departmentRepository.save(dept);
        return RequestResponse.builder()
                .statusCode(200)
                .message("department updated successfully")
                .build();
    }


    public RequestResponse getDepartmentById(Integer deptId) throws NotFoundException {
        Department dept = departmentRepository.findById(deptId).orElseThrow(()-> new NotFoundException());
        DepartmentDTO deptDto = entitydtomapper.mapDeptToBasic(dept);
        return RequestResponse.builder()
                .statusCode(200)
                .departmentdto(deptDto)
                .build();
    }


    public RequestResponse deleteDepartment(Integer deptId) throws NotFoundException {
        Department dept = departmentRepository.findById(deptId).orElseThrow(()-> new NotFoundException());
        departmentRepository.delete(dept);
        return RequestResponse.builder()
                .statusCode(200)
                .message("Department was deleted successfully")
                .build();
    }
}
