package com.placetrack.config;

import com.placetrack.model.Admin;
import com.placetrack.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public void run(String... args) {
        // Create default admin if not exists
        if (adminRepository.findByUsername("admin").isEmpty()) {
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword("admin123");
            admin.setFullName("System Admin");
            adminRepository.save(admin);
            System.out.println("✅ Default admin created: username=admin, password=admin123");
        }
    }
}
