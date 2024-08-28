package com.ashville.usermanagementsystem.mapper;

import org.springframework.stereotype.Component;

import com.ashville.usermanagementsystem.DTO.CheckInDTO;
import com.ashville.usermanagementsystem.DTO.DepartmentDTO;
import com.ashville.usermanagementsystem.DTO.UserDTO;
import com.ashville.usermanagementsystem.entity.CheckIn;
import com.ashville.usermanagementsystem.entity.Department;
import com.ashville.usermanagementsystem.entity.OurUsers;

@Component
public class entityDTOmapper {
    
    public UserDTO mapUserToDtoBasic(OurUsers user){
        UserDTO userDto = new UserDTO();
        userDto.setUserId(user.getUserId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        userDto.setPassword(user.getPassword());
        userDto.setMobile(user.getMobile());
        userDto.setCity(user.getCity());
        return userDto;

    }

    public DepartmentDTO mapDeptToBasic(Department dept){
        DepartmentDTO deptDTO = new DepartmentDTO();
        deptDTO.setDepartmentId(dept.getDepartmentId());
        deptDTO.setDeptName(dept.getDeptName());
        return deptDTO;
    }

    public CheckInDTO mapCheckInToBasic(CheckIn check){
        CheckInDTO checkDTO = new CheckInDTO();
        checkDTO.setCheckin_id(check.getCheckInId());
        checkDTO.setCheckinTime(check.getCheckinTime());
        checkDTO.setCheckoutTime(check.getCheckoutTime());
        return checkDTO;
    }
}
