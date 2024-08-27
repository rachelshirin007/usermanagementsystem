package com.ashville.usermanagementsystem.controller;

import com.ashville.usermanagementsystem.DTO.RequestResponse;
import com.ashville.usermanagementsystem.entity.Department;
import com.ashville.usermanagementsystem.entity.OurUsers;
import com.ashville.usermanagementsystem.repository.DepartmentRepository;
import com.ashville.usermanagementsystem.services.UserManagementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private DepartmentRepository departmentRepository;


    // Create a new department
    @PostMapping("/admin/depts")
    public ResponseEntity<RequestResponse> registerDepts(@RequestBody RequestResponse res) {
        System.out.println("=============in registerDepts===============");

        return ResponseEntity.ok(userManagementService.registerDept(res));
    }

    @GetMapping("/admin/get-all-depts")
    public ResponseEntity<RequestResponse> getAllDepts() 
    {
        System.out.println("=============in getAllDepts===============");

        return ResponseEntity.ok(userManagementService.getAllDepts());
    }

    // Get all users by department ID
    @GetMapping("/admin/depts/{id}/users")
    public List<OurUsers> getUsersByDepartmentId(@PathVariable Integer id) {
        return departmentRepository.findByDepartmentId(id);
    }

    // Update department
    @PutMapping("/admin/depts/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Integer id, @RequestBody Department departmentDetails) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        department.setDeptName(departmentDetails.getDeptName());
        Department updatedDepartment = departmentRepository.save(department);
        return ResponseEntity.ok(updatedDepartment);
    }

    // Delete department
    @DeleteMapping("/admin/depts/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Integer id) {
        departmentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
