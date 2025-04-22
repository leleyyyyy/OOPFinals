package com.CareNet.CN.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor // Add this annotation for a no-args constructor
@Entity
@Table(name = "patient_assessments")
public class PatientAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int age;
    private LocalDate date; // Assuming you want to use LocalDate for the date
    private double height;
    private double weight;
    private String symptomsOrNotes;
    private String treatmentPlan;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Assuming each assessment is linked to a user
    private User user;

    // Constructor for easy instantiation
    public PatientAssessment(int age, LocalDate date, double height, double weight, String notes, String symptomsOrNotes, String treatmentPlan) {
        this.age = age;
        this.date = date;
        this.height = height;
        this.weight = weight;
        this.symptomsOrNotes = symptomsOrNotes;
        this.treatmentPlan = treatmentPlan;
    }
}