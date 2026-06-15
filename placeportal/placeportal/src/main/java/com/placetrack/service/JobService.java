package com.placetrack.service;

import com.placetrack.model.Job;
import com.placetrack.model.Student;
import com.placetrack.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public Job postJob(Job job) {
        return jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public List<Job> getOpenJobs() {
        return jobRepository.findByStatus(Job.JobStatus.OPEN);
    }

    public Job getById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
    }

    public void updateJobStatus(Long id, Job.JobStatus status) {
        Job job = getById(id);
        job.setStatus(status);
        jobRepository.save(job);
    }

    /**
     * Eligibility check:
     * 1. CGPA >= minCgpa
     * 2. Branch matches (or job allows ALL)
     * 3. At least one required skill present in student skills
     */
    public boolean isEligible(Student student, Job job) {
        // Check CGPA
        if (job.getMinCgpa() != null && student.getCgpa() != null) {
            if (student.getCgpa() < job.getMinCgpa()) {
                return false;
            }
        }

        // Check Branch
        if (job.getRequiredBranch() != null && !job.getRequiredBranch().equalsIgnoreCase("ALL")) {
            List<String> requiredBranches = Arrays.asList(
                    job.getRequiredBranch().split(","));
            boolean branchMatches = requiredBranches.stream()
                    .anyMatch(b -> b.trim().equalsIgnoreCase(
                            student.getBranch() != null ? student.getBranch().trim() : ""));
            if (!branchMatches) return false;
        }

        // Check Passing Year
        if (job.getPassingYear() != null && !job.getPassingYear().isBlank()) {
            if (!job.getPassingYear().equalsIgnoreCase(student.getPassingYear())) {
                return false;
            }
        }

        // Check Skills (at least one match)
        if (job.getRequiredSkills() != null && !job.getRequiredSkills().isBlank()) {
            if (student.getSkills() == null || student.getSkills().isBlank()) {
                return false;
            }
            List<String> jobSkills = Arrays.stream(job.getRequiredSkills().split(","))
                    .map(String::trim).map(String::toLowerCase).toList();
            List<String> studentSkills = Arrays.stream(student.getSkills().split(","))
                    .map(String::trim).map(String::toLowerCase).toList();
            boolean skillMatch = jobSkills.stream().anyMatch(studentSkills::contains);
            if (!skillMatch) return false;
        }

        return true;
    }
}
