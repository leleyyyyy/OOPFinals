package com.CareNet.CN.service;

import com.CareNet.CN.model.DoctorAppointment;
import com.CareNet.CN.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;

    }

    public List<DoctorAppointment> getPendingPatients() {
        return patientRepository.findByStatus(DoctorAppointment.Status.PENDING);
    }

    public void savePatient(DoctorAppointment doctorAppointment) {
        patientRepository.save(doctorAppointment);
    }

    public DoctorAppointment acceptPatient(Long patientId) {
        DoctorAppointment doctorAppointment = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with id: " + patientId));
        doctorAppointment.setStatus(DoctorAppointment.Status.ACCEPTED);
        return patientRepository.save(doctorAppointment);
    }

    public DoctorAppointment declinePatient(Long patientId) {
        DoctorAppointment doctorAppointment = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with id: " + patientId));
        doctorAppointment.setStatus(DoctorAppointment.Status.DECLINED);
        return patientRepository.save(doctorAppointment);
    }
}
