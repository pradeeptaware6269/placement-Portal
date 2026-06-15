package com.placetrack.service;

import com.placetrack.model.Admin;
import com.placetrack.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Optional<Admin> login(String username, String password) {
        return adminRepository.findByUsername(username)
                .filter(a -> a.getPassword().equals(password));
    }
}
