package com.placetrack.service;

import com.placetrack.model.Application;
import com.placetrack.model.Job;
import com.placetrack.model.Student;
import com.placetrack.repository.ApplicationRepository;
import com.placetrack.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private StudentRepository studentRepository;

    public Application apply(Student student, Job job) {
        if (applicationRepository.existsByStudentAndJob(student, job)) {
            throw new RuntimeException("Already applied for this job!");
        }
        Application app = new Application();
        app.setStudent(student);
        app.setJob(job);
        app.setAppliedAt(LocalDateTime.now());
        app.setStatus(Application.ApplicationStatus.APPLIED);
        return applicationRepository.save(app);
    }

    public List<Application> getByStudent(Student student) {
        return applicationRepository.findByStudent(student);
    }

    public List<Application> getByJob(Job job) {
        return applicationRepository.findByJob(job);
    }

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    public void updateStatus(Long applicationId, Application.ApplicationStatus status) {
        Application app = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        app.setStatus(status);
        applicationRepository.save(app);

        // If selected, mark student as PLACED
        if (status == Application.ApplicationStatus.SELECTED) {
            Student student = app.getStudent();
            student.setPlacementStatus(Student.PlacementStatus.PLACED);
            studentRepository.save(student);
        }
    }
}
