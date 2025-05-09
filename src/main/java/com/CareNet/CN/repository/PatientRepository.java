package com.CareNet.CN.repository;

import com.CareNet.CN.model.DoctorAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface PatientRepository  extends JpaRepository<DoctorAppointment, Long> {
    List<DoctorAppointment> findByStatus(DoctorAppointment.Status status);
}
