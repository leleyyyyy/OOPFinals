package com.CareNet.CN.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentSetter {
    private String name;
    private LocalDateTime appointmentDate;
}