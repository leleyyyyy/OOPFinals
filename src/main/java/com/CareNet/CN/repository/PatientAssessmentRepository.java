package com.CareNet.CN.repository;

import com.CareNet.CN.model.PatientAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientAssessmentRepository extends JpaRepository<PatientAssessment, Long> {
    // Custom query method to find assessments by username
    List<PatientAssessment> findAllByUsername(String username);
}