package com.placetrack.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String fullName;
    private String phone;
    private String course;
    private String branch;
    private String passingYear;
    private Double cgpa;
    private String skills;   // comma-separated
    private String address;

    @Enumerated(EnumType.STRING)
    private PlacementStatus placementStatus = PlacementStatus.NOT_PLACED;

    public enum PlacementStatus {
        NOT_PLACED, PLACED
    }
}
