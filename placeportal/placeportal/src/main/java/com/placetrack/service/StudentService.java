package com.placetrack.service;

import com.placetrack.model.Student;
import com.placetrack.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student register(Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new RuntimeException("Email already registered!");
        }
        return studentRepository.save(student);
    }

    public Optional<Student> login(String email, String password) {
        return studentRepository.findByEmail(email)
                .filter(s -> s.getPassword().equals(password));
    }

    public Student getById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public Student updateProfile(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> getPlacedStudents() {
        return studentRepository.findAll().stream()
                .filter(s -> s.getPlacementStatus() == Student.PlacementStatus.PLACED)
                .toList();
    }
}
