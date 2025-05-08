package com.CareNet.CN.repository;

import com.CareNet.CN.model.Doctor;
import com.CareNet.CN.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByUser(User user);
}