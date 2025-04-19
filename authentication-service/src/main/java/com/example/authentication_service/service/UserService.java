package com.example.authentication_service.service;

import com.example.authentication_service.models.User;

import java.util.List;

public interface UserService {
    public User saveDoctor(User doctor);
    public List<User> getAllDoctors();
    public User findById(int id) throws Exception;


}
