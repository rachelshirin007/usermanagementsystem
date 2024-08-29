package com.ashville.usermanagementsystem.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ashville.usermanagementsystem.DTO.LoginRequestDTO;
import com.ashville.usermanagementsystem.DTO.RequestResponse;
import com.ashville.usermanagementsystem.DTO.UserDTO;
import com.ashville.usermanagementsystem.entity.OurUsers;
import com.ashville.usermanagementsystem.exception.InvalidCredentialsException;
import com.ashville.usermanagementsystem.exception.NotFoundException;
import com.ashville.usermanagementsystem.mapper.entityDTOmapper;
import com.ashville.usermanagementsystem.repository.UsersRepo;

import java.util.List;
import java.util.Optional;

@Service
public class UserServImpl implements UserDetailsService{
    

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private JWTUtils jwtUtils;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private entityDTOmapper entitydtomapper;

    public RequestResponse registerUser(UserDTO userReg){
        OurUsers user = new OurUsers();
        user.setName(userReg.getName());
        user.setEmail(userReg.getEmail());
        user.setPassword(passwordEncoder.encode(userReg.getPassword()));
        user.setCity(userReg.getCity());
        user.setRole(userReg.getRole());
        user.setMobile(userReg.getMobile());
        user.setDepartment(userReg.getDepartment());
        usersRepo.save(user);
        return RequestResponse.builder()
                .statusCode(200)
                .message("User created successfully")
                .build();
    }

    public RequestResponse loginUser(LoginRequestDTO loginRequest) {

        OurUsers user = usersRepo.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new NotFoundException("Email not found"));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Password does not match");
        }
        String token = jwtUtils.generateToken(user);

        return RequestResponse.builder()
                .statusCode(200)
                .message("User Successfully Logged In")
                .token(token)
                .expirationTime("6 Month")
                .build();
    }

   /* public RequestResponse refreshToken(RequestResponse refreshTokenReqiest){
        
        try{
            String ourEmail = jwtUtils.getUsernameFromToken(refreshTokenReqiest.getToken());
            OurUsers users = usersRepo.findByEmail(ourEmail).orElseThrow();
            if (jwtUtils.isTokenValid(refreshTokenReqiest.getToken(), users)) {
                var jwt = jwtUtils.generateToken(users);
                RequestResponse.builder().token(jwt);
            
            return RequestResponse.builder()
            .statusCode(200)
            .token(jwt)
            .refreshToken(refreshTokenReqiest.getToken())
            .expirationTime("24Hrs")
            .message("Successfully Refreshed Token")
            .build();
            }
            else{
                return RequestResponse.builder()
            .statusCode(500)
            .error("Unsuccessful Refreshed Token")
            .build();

            }

        }catch (Exception e){
            return RequestResponse.builder()
                .statusCode(500)
                .error("Unable to refresh token")
                .build();
        }
    }
 */

    public RequestResponse getAllUsers() {

        List<OurUsers> users = usersRepo.findAll();
        List<UserDTO> userDtos = users.stream()
                .map(entitydtomapper::mapUserToDtoBasic)
                .toList();

        return RequestResponse.builder()
                .statusCode(200)
                .ourUsersList(userDtos)
                .build();
    }

    public RequestResponse getUsersById(Integer id) {
        try {
            OurUsers usersById = usersRepo.findById(id).orElseThrow(() -> new RuntimeException("User Not found"));
            return RequestResponse.builder()
            .users(usersById)
            .statusCode(200)
            .message("Users with id '" + id + "' found successfully")
            .build();
            
        } catch (Exception e) {
            return RequestResponse.builder()
            .statusCode(500)
            .error("Could find User")
            .build();
        }
    }


    public RequestResponse deleteUser(Integer userId) {
        try {
            Optional<OurUsers> userOptional = usersRepo.findById(userId);
            if (userOptional.isPresent()) {
                usersRepo.deleteById(userId);
                return RequestResponse.builder()
                .statusCode(200)
                .message("User deleted successfully")
                .build();
            } else {
                return RequestResponse.builder()
                .statusCode(400)
                .error("User not found for deletion")
                .build();
            }
        } catch (Exception e) {
            return RequestResponse.builder()
                .statusCode(500)
                .error("Error occurred while deleting user")
                .build();
        }
    }

    public RequestResponse updateUser(Integer userId, OurUsers updatedUser) {
        try {
            Optional<OurUsers> userOptional = usersRepo.findById(userId);
            if (userOptional.isPresent()) {
                OurUsers existingUser = userOptional.get();
                existingUser.setEmail(updatedUser.getEmail());
                existingUser.setName(updatedUser.getName());
                existingUser.setCity(updatedUser.getCity());
                existingUser.setRole(updatedUser.getRole());
                existingUser.setMobile(updatedUser.getMobile());
                existingUser.setDepartment(updatedUser.getDepartment());

                // Check if password is present in the request
                if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                    // Encode the password and update it
                    existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                }
                System.out.println("Now saving to User entity");
                OurUsers savedUser = usersRepo.save(existingUser);
                System.out.println("saved user" + savedUser);

                return RequestResponse.builder()
                .users(savedUser)
                .statusCode(200)
                .message("User updated successfully")
                .build();
            } else {
                return RequestResponse.builder()
                .statusCode(404)
                .error("User not found for update")
                .build();
            }
        } catch (Exception e) {
            return RequestResponse.builder()
                .statusCode(500)
                .error("Error occurred while updating user")
                .build();
        }
    }


    public RequestResponse getMyInfo(String email){
        try {
            Optional<OurUsers> userOptional = usersRepo.findByEmail(email);
            if (userOptional.isPresent()) {
                return RequestResponse.builder()
                .users(userOptional.get())
                .statusCode(200)
                .message("successful in getting my info")
                .build();
                //reqRes.setMessage("successful");
            } else {
                return RequestResponse.builder()
                .statusCode(404)
                .message("User not found")
                .build();
            }

        }catch (Exception e){
            return RequestResponse.builder()
                .statusCode(500)
                .error("User not found")
                .build();
            }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }


}
