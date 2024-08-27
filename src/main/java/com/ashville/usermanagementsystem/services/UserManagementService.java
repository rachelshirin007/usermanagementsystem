package com.ashville.usermanagementsystem.services;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ashville.usermanagementsystem.DTO.RequestResponse;
import com.ashville.usermanagementsystem.entity.OurUsers;
import com.ashville.usermanagementsystem.entity.Department;
import com.ashville.usermanagementsystem.repository.DepartmentRepository;
import com.ashville.usermanagementsystem.repository.UsersRepo;
import java.util.List;
import java.util.Optional;

@Service
public class UserManagementService {
    

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public RequestResponse register(RequestResponse registrationRequest){
        RequestResponse resp = new RequestResponse();
        
        try{
            OurUsers ourUsers = new OurUsers();
            ourUsers.setEmail(registrationRequest.getEmail());
            ourUsers.setCity(registrationRequest.getCity());
            ourUsers.setRole(registrationRequest.getRole());
            ourUsers.setName(registrationRequest.getName());
            ourUsers.setMobile(registrationRequest.getMobile());
            ourUsers.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

            OurUsers ourUsersResult = usersRepo.save(ourUsers);

            if(ourUsersResult.getUserId()>0){
                resp.setOurUsers(ourUsersResult);
               System.out.println("User saved Successfully");
                resp.setStatusCode(200);
            }

        }catch(Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public RequestResponse login(RequestResponse loginRequest){
        RequestResponse response = new RequestResponse();

        try{
            authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), 
            loginRequest.getPassword()));

            var user = usersRepo.findByEmail(loginRequest.getEmail()).orElseThrow();
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hrs");
            System.out.println("Successfully Logged in");

            //response.setMessage("Successfully Logged in");
        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public RequestResponse refreshToken(RequestResponse refreshTokenReqiest){
        RequestResponse response = new RequestResponse();
        try{
            String ourEmail = jwtUtils.extractUsername(refreshTokenReqiest.getToken());
            OurUsers users = usersRepo.findByEmail(ourEmail).orElseThrow();
            if (jwtUtils.isTokenValid(refreshTokenReqiest.getToken(), users)) {
                var jwt = jwtUtils.generateToken(users);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(refreshTokenReqiest.getToken());
                response.setExpirationTime("24Hr");
                System.out.println("Successfully Refreshed Token");
                //response.setMessage("Successfully Refreshed Token");
            }
            response.setStatusCode(200);
            return response;

        }catch (Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
            return response;
        }
    }


    public RequestResponse getAllUsers() {
        RequestResponse reqRes = new RequestResponse();

        try {
            List<OurUsers> result = usersRepo.findAll();
            if (!result.isEmpty()) {
                reqRes.setOurUsersList(result);
                reqRes.setStatusCode(200);
                System.out.println("Successfully Got All Users");

                //reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setError("No users found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setError("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }


    public RequestResponse getUsersById(Integer id) {
        RequestResponse reqRes = new RequestResponse();
        try {
            OurUsers usersById = usersRepo.findById(id).orElseThrow(() -> new RuntimeException("User Not found"));
            reqRes.setOurUsers(usersById);
            reqRes.setStatusCode(200);
            System.out.println("Users with id '" + id + "' found successfully");

            //reqRes.setMessage("Users with id '" + id + "' found successfully");
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setError("Error occurred: " + e.getMessage());
        }
        return reqRes;
    }


    public RequestResponse deleteUser(Integer userId) {
        RequestResponse reqRes = new RequestResponse();
        try {
            Optional<OurUsers> userOptional = usersRepo.findById(userId);
            if (userOptional.isPresent()) {
                usersRepo.deleteById(userId);
                reqRes.setStatusCode(200);
                //reqRes.setMessage("User deleted successfully");
                System.out.println("User deleted successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setError("User not found for deletion");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setError("Error occurred while deleting user: " + e.getMessage());
        }
        return reqRes;
    }

    public RequestResponse updateUser(Integer userId, OurUsers updatedUser) {
        RequestResponse reqRes = new RequestResponse();
        try {
            Optional<OurUsers> userOptional = usersRepo.findById(userId);
            if (userOptional.isPresent()) {
                OurUsers existingUser = userOptional.get();
                existingUser.setEmail(updatedUser.getEmail());
                existingUser.setName(updatedUser.getName());
                existingUser.setCity(updatedUser.getCity());
                existingUser.setRole(updatedUser.getRole());

                // Check if password is present in the request
                if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                    // Encode the password and update it
                    existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                }

                OurUsers savedUser = usersRepo.save(existingUser);
                reqRes.setOurUsers(savedUser);
                reqRes.setStatusCode(200);
                System.out.println("User updated successfully");

                //reqRes.setMessage("User updated successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setError("User not found for update");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setError("Error occurred while updating user: " + e.getMessage());
        }
        return reqRes;
    }


    public RequestResponse getMyInfo(String email){
        RequestResponse reqRes = new RequestResponse();
        try {
            Optional<OurUsers> userOptional = usersRepo.findByEmail(email);
            if (userOptional.isPresent()) {
                reqRes.setOurUsers(userOptional.get());
                reqRes.setStatusCode(200);
                System.out.println("successful in getting my info");
                //reqRes.setMessage("successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setError("User not found for update");
            }

        }catch (Exception e){
            reqRes.setStatusCode(500);
            reqRes.setError("Error occurred while getting user info: " + e.getMessage());
        }
        return reqRes;

    }

    public RequestResponse registerDept(RequestResponse registrationRequest){
        RequestResponse resp = new RequestResponse();
        
        try{
            Department department = new Department();
            System.out.println("=============deptName==============="+ registrationRequest.getDeptName());

            department.setDeptName(registrationRequest.getDeptName());
            Department departmentResult = departmentRepository.save(department);

            if(departmentResult.getDepartmentId()>0){
                resp.setDepartment(departmentResult);
               System.out.println("Department saved Successfully");
                resp.setStatusCode(200);
            }

        }catch(Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public RequestResponse getAllDepts() {
        RequestResponse reqRes = new RequestResponse();

        try {
            List<Department> result = departmentRepository.findAll();
            System.out.println("=============dept repo result===============" + result);

            if (!result.isEmpty()) {
                reqRes.setDepartmentList(result);
                reqRes.setStatusCode(200);
                System.out.println("Successfully Got All Depts");

                //reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setError("No depts found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setError("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }

}
