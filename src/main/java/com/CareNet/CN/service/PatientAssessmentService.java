package com.CareNet.CN.service;

import com.CareNet.CN.model.PatientAssessment;

import java.util.List;

public interface PatientAssessmentService {
    List<PatientAssessment> getAssessmentsByUsername(String username);
}