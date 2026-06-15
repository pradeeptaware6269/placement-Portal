package com.placetrack.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "jobs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String role;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    private String requiredSkills;       // comma-separated
    private String requiredBranch;       // comma-separated branches, or "ALL"
    private Double minCgpa;
    private String passingYear;
    private Double packageOffered;       // in LPA
    private LocalDate applicationDeadline;
    private String jobDescription;

    @Enumerated(EnumType.STRING)
    private JobStatus status = JobStatus.OPEN;

    public enum JobStatus {
        OPEN, CLOSED
    }
}
