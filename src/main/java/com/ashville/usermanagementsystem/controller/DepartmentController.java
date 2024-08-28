package com.ashville.usermanagementsystem.controller;

import com.ashville.usermanagementsystem.DTO.DepartmentDTO;
import com.ashville.usermanagementsystem.DTO.RequestResponse;
import com.ashville.usermanagementsystem.services.DepartmentServImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentServImpl departmentServImpl;

    @PostMapping("/create-dept")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RequestResponse> createDepartment(@RequestBody DepartmentDTO departmentDTO){
        return ResponseEntity.ok(departmentServImpl.registerDept(departmentDTO));
    }

    @GetMapping("/get-all-depts")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RequestResponse> getAllDepartment(){
        return ResponseEntity.ok(departmentServImpl.getAllDepts());
    }

    @PutMapping("/update-dept/{deptId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RequestResponse> updateDept(@PathVariable Integer deptId, @RequestBody DepartmentDTO departmentDTO) throws NotFoundException{
        return ResponseEntity.ok(departmentServImpl.updateDept(deptId, departmentDTO));
    }

    @DeleteMapping("/delete-dept/{deptId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RequestResponse> deleteDept(@PathVariable Integer deptId) throws NotFoundException{
        return ResponseEntity.ok(departmentServImpl.deleteDepartment(deptId));
    }

    @GetMapping("/get-dept-by-id/{deptId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RequestResponse> getDeptById(@PathVariable Integer deptId) throws NotFoundException{
        return ResponseEntity.ok(departmentServImpl.getDepartmentById(deptId));
    }
}
