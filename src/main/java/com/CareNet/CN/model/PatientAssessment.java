package com.CareNet.CN.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "patient_assessments")
public class PatientAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int age;
    private LocalDate date;
    private double height;
    private double weight;
    private String notes;
    private String symptomsOrNotes;

    @Column(name = "treatment_plan") // Ensure this matches the database column name
    private String treatmentPlan;

    @Column(name = "username", nullable = false) // Field to capture username
    private String username;

    // If you want to keep a reference to the User entity
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // This should match your database schema
    private User user;

    // Constructor for easy instantiation
    public PatientAssessment(int age, LocalDate date, double height, double weight, String notes, String symptomsOrNotes, String treatmentPlan, String username) {
        this.age = age;
        this.date = date;
        this.height = height;
        this.weight = weight;
        this.notes = notes;
        this.symptomsOrNotes = symptomsOrNotes;
        this.treatmentPlan = treatmentPlan;
        this.username = username; // Set the username
    }
}