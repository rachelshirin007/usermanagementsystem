package com.ashville.usermanagementsystem.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import com.ashville.usermanagementsystem.DTO.RequestResponse;
import com.ashville.usermanagementsystem.entity.OurUsers;
import com.ashville.usermanagementsystem.services.OurUserDetailsService;
import com.ashville.usermanagementsystem.services.UserManagementService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
public class UserManagementController {
    
    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private OurUserDetailsService ourUserDetailsService;

    @PostMapping("/auth/register")
    public ResponseEntity<RequestResponse> register(@RequestBody RequestResponse res) {
        
        return ResponseEntity.ok(userManagementService.register(res));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<RequestResponse> login(@RequestBody RequestResponse res) {
        
        return ResponseEntity.ok(userManagementService.login(res));
    }
    
    @PostMapping("/auth/refresh")
    public ResponseEntity<RequestResponse> refreshToken(@RequestBody RequestResponse res) {
        
        return ResponseEntity.ok(userManagementService.refreshToken(res));
    }

    @GetMapping("/admin/get-all-users")
    public ResponseEntity<RequestResponse> getAllUsers() 
    {
        return ResponseEntity.ok(userManagementService.getAllUsers());
    }

    @GetMapping("/admin/get-users/{userId}")
    public ResponseEntity<RequestResponse> getUserById(@PathVariable Integer userId) 
    {
        return ResponseEntity.ok(userManagementService.getUsersById(userId));
    }

    @PutMapping("/admin/update/{userId}")
    public ResponseEntity<RequestResponse> updateUser(@PathVariable Integer userId, @RequestBody OurUsers reqres) 
    {
        return ResponseEntity.ok(userManagementService.updateUser(userId, reqres));
    }

    @GetMapping("/adminuser/get-profile")
    public ResponseEntity<RequestResponse> getProfile() 
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        RequestResponse response = userManagementService.getMyInfo(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/admin/delete/{userId}")
    public ResponseEntity<RequestResponse> deleteUSer(@PathVariable Integer userId){
        return ResponseEntity.ok(userManagementService.deleteUser(userId));
    }
    
    @GetMapping("/department/{departmentId}")
    public List<OurUsers> getUsersByDepartment(@PathVariable Integer departmentId) {
        return ourUserDetailsService.getUsersByDepartmentId(departmentId);
    }

    @DeleteMapping("/department/{departmentId}")
    public void deleteUsersByDepartment(@PathVariable Integer departmentId) {
        ourUserDetailsService.deleteUsersByDepartmentId(departmentId);
    }
}
