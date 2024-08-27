package com.ashville.usermanagementsystem.services;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ashville.usermanagementsystem.entity.OurUsers;
import com.ashville.usermanagementsystem.repository.DepartmentRepository;
import com.ashville.usermanagementsystem.repository.UsersRepo;

@Service
public class OurUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepo.findByEmail(username).orElseThrow();
    }

    public List<OurUsers> getUsersByDepartmentId(Integer departmentId) {
        return departmentRepository.findByDepartmentId(departmentId);
    }

    public void deleteUsersByDepartmentId(Integer departmentId) {
        departmentRepository.deleteByDepartmentId(departmentId);
    }
}