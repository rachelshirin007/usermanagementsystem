package com.ashville.usermanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.ashville.usermanagementsystem.entity.OurUsers;

import java.util.Optional;


@EnableJpaRepositories(basePackages = "com.ashville.usermanagementsystem.repository")
public interface UsersRepo extends JpaRepository<OurUsers, Integer>{

     Optional<OurUsers> findByEmail(String email);
     //OurUsers findByUserId(Integer id);
   
    
}
