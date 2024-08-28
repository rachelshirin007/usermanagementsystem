package com.ashville.usermanagementsystem.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ashville.usermanagementsystem.entity.OurUsers;
import com.ashville.usermanagementsystem.exception.NotFoundException;
import com.ashville.usermanagementsystem.repository.UsersRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        OurUsers user;
        
            user = userRepo.findByEmail(username)
            .orElseThrow(()-> new NotFoundException("User/ Email Not found"));
        

        return AuthUser.builder()
                .user(user)
                .build();
    }
}
