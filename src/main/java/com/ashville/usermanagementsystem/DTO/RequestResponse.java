package com.ashville.usermanagementsystem.DTO;

import java.time.LocalDateTime;
import java.util.List;

import com.ashville.usermanagementsystem.entity.OurUsers;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestResponse {

    
    private int statusCode;
    private String error;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String message;
    private final LocalDateTime timestamp = LocalDateTime.now();
    
    private OurUsers users;
    private UserDTO ourUsersdto;
    private DepartmentDTO departmentdto;
    private CheckInDTO checkIndto;
    private List<CheckInDTO> checkInList;
    private List<UserDTO> ourUsersList;
    private List<DepartmentDTO> departmentList;
    
}
