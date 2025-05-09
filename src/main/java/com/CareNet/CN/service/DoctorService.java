package com.CareNet.CN.service;

import com.CareNet.CN.model.Doctor;
import com.CareNet.CN.model.User;
import com.CareNet.CN.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public Optional<Doctor> getDoctorByUser(User user) {
        return doctorRepository.findByUser(user);
    }
}