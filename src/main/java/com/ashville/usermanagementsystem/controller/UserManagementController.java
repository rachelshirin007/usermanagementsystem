package com.ashville.usermanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import com.ashville.usermanagementsystem.DTO.LoginRequestDTO;
import com.ashville.usermanagementsystem.DTO.RequestResponse;
import com.ashville.usermanagementsystem.DTO.UserDTO;
import com.ashville.usermanagementsystem.entity.OurUsers;
import com.ashville.usermanagementsystem.services.UserServImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
public class UserManagementController {
    
    @Autowired
    private UserServImpl userManagementService;


    @PostMapping("/auth/register")
    public ResponseEntity<RequestResponse> register(@RequestBody UserDTO userdto) {
        
        return ResponseEntity.ok(userManagementService.registerUser(userdto));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<RequestResponse> login(@RequestBody LoginRequestDTO logindto) {
        
        return ResponseEntity.ok(userManagementService.loginUser(logindto));
    }
   /* 
    @PostMapping("/auth/refresh")
    public ResponseEntity<RequestResponse> refreshToken(@RequestBody RequestResponse res) {
        
        return ResponseEntity.ok(userManagementService.refreshToken(res));
    } */

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
    
    
}
