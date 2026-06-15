package com.placetrack.repository;

import com.placetrack.model.Application;
import com.placetrack.model.Student;
import com.placetrack.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByStudent(Student student);
    List<Application> findByJob(Job job);
    boolean existsByStudentAndJob(Student student, Job job);
    List<Application> findByStatus(Application.ApplicationStatus status);
}
