package com.example.authentication_service.service;

import com.example.authentication_service.models.User;
import com.example.authentication_service.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    com.example.authentication_service.repos.UserRepo doctorRepo;

    @Override
    public User saveDoctor(User doctor) {
        return doctorRepo.save(doctor);
    }

    @Override
    public List<User> getAllDoctors() {
        return doctorRepo.findAll();
    }

    @Override
    public User findById(int id) throws Exception {
        Optional<User> doctor = doctorRepo.findById(id);
       if(doctor.isEmpty() || !doctor.get().getRole().equals(Role.ROLE_DOCTOR))
       {
           throw new Exception("Doctor not found with this id");
       }
        return doctor.get();
    }


}
