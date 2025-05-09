package com.CareNet.CN.service;

import com.CareNet.CN.model.Patient;
import com.CareNet.CN.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;

    }

    public List<Patient> getPendingPatients() {
        return patientRepository.findByStatus(Patient.Status.PENDING);
    }

    public void savePatient(Patient patient) {
        patientRepository.save(patient);
    }

    public Patient acceptPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with id: " + patientId));
        patient.setStatus(Patient.Status.ACCEPTED);
        return patientRepository.save(patient);
    }

    public Patient declinePatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with id: " + patientId));
        patient.setStatus(Patient.Status.DECLINED);
        return patientRepository.save(patient);
    }
}
