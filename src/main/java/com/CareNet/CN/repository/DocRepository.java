package com.CareNet.CN.repository;

import com.CareNet.CN.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocRepository extends JpaRepository<Doctor, Long> {
    Doctor findByEmail(String email); // Find doctor by email if needed
}
