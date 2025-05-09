package com.CareNet.CN.service;

import com.CareNet.CN.model.Doctor;
import com.CareNet.CN.repository.DocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocService {

    @Autowired
    private DocRepository docRepository;

    public Doctor saveDoctor(Doctor doctor) {
        return docRepository.save(doctor); // Save the doctor in the repository
    }
}
