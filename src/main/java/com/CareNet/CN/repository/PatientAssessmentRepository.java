package com.CareNet.CN.repository;

import com.CareNet.CN.model.PatientAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientAssessmentRepository extends JpaRepository<PatientAssessment, Long> {
    // You can define custom query methods here if needed
}