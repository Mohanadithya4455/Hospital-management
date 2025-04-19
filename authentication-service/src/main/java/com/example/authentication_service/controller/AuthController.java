package com.example.authentication_service.controller;

import com.example.authentication_service.config.JwtTokenProvider;
import com.example.authentication_service.models.User;
import com.example.authentication_service.models.Role;
import com.example.authentication_service.repos.UserRepo;
import com.example.authentication_service.requests.LogIn;
import com.example.authentication_service.responses.AuthResponse;
import com.example.authentication_service.service.AuthService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthController {



    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserRepo doctorRepo;
    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/signup")
    private ResponseEntity<AuthResponse> createUser(@RequestBody User doctor) throws Exception {
        User isExist=doctorRepo.findByEmail(doctor.getEmail());
        if(isExist!=null){
            throw new Exception("Doctor already Exists");
        }
        User newUser=new User();
        newUser.setEmail(doctor.getEmail());
//        newUser.setName(doctor.getName());
        newUser.setPassword(passwordEncoder.encode(doctor.getPassword()));
        newUser.setRole(doctor.getRole());
        doctorRepo.save(newUser);

        AuthResponse authResponse=new AuthResponse();

        Authentication auth=new UsernamePasswordAuthenticationToken(newUser.getEmail(),newUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt=jwtTokenProvider.generateToken(auth);
        authResponse.setJwt(jwt);
        authResponse.setMessage("Registered Successfully");
        authResponse.setRole(newUser.getRole());
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(@RequestBody LogIn login) throws Exception {
        String username= login.getEmail();
        String password=login.getPassword();
        Authentication authentication=createAuth(username,password);
        Collection<? extends GrantedAuthority> authorities=authentication.getAuthorities();
        String role=authorities.isEmpty()?null:authorities.iterator().next().getAuthority();
        String jwt=jwtTokenProvider.generateToken(authentication);
        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Logged in successfully");
        authResponse.setRole(Role.valueOf(role));
        return new ResponseEntity<>(authResponse,HttpStatus.OK);
    }
    public Authentication createAuth(String username,String password) throws Exception{
        UserDetails userDetails=authService.loadUserByUsername(username);
        if(userDetails==null){
            throw new UsernameNotFoundException("No user with this email");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Credentials");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
    @GetMapping("/get_user")
    public User getUserDetails(@RequestHeader("Authorization") String jwt){
        String email = jwtTokenProvider.getEmailFromToken(jwt);
        User user = userRepo.findByEmail(email);
        return user;
    }

    @GetMapping("/validate")
    public Claims validateJwt(@RequestParam("jwt") String jwt){
       return jwtTokenProvider.validateToken(jwt);
    }
}
