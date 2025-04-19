package com.example.authentication_service.service;

import com.example.authentication_service.models.User;
import com.example.authentication_service.models.Role;
import com.example.authentication_service.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    private UserRepo doctorRepo;
    @Override
    public  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=doctorRepo.findByEmail(username);
        if(user==null){
            throw new UsernameNotFoundException("User not found with this email: "+username);
        }
        Role role=user.getRole();
        List<GrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }
}