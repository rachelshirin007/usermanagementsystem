package com.ashville.usermanagementsystem.repository;

import com.ashville.usermanagementsystem.entity.Department;
import com.ashville.usermanagementsystem.entity.OurUsers;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories(basePackages = "com.ashville.usermanagementsystem.repository")
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    List<OurUsers> findByDepartmentId(Integer departmentId); // Find users by departmentId

    void deleteByDepartmentId(Integer departmentId); // Delete users by departmentId
    
}
