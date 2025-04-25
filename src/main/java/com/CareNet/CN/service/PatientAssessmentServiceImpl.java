package com.CareNet.CN.service;

import com.CareNet.CN.model.PatientAssessment;
import com.CareNet.CN.repository.PatientAssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientAssessmentServiceImpl implements PatientAssessmentService {

    @Autowired
    private PatientAssessmentRepository patientAssessmentRepository;

    @Override
    public List<PatientAssessment> getAssessmentsByUsername(String username) {
        List<PatientAssessment> assessments = patientAssessmentRepository.findAllByUsername(username);
        System.out.println("Fetched assessments for user " + username + ": " + assessments); // Debug log
        return assessments;
    }
}