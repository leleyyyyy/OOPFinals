package com.CareNet.CN.repository;

import com.CareNet.CN.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface PatientRepository  extends JpaRepository<Patient, Long> {
    List<Patient> findByStatus(Patient.Status status);
}
