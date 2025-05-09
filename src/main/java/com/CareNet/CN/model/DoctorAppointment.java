package com.CareNet.CN.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "appointments")
public class DoctorAppointment {
    public enum Status {
        PENDING,
        ACCEPTED,
        DECLINED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String name;

    @Setter
    private String datetime;

    @Setter
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    public DoctorAppointment() {
    }

    public DoctorAppointment(String name, String datetime) {
        this.name = name;
        this.datetime = datetime;
        this.status = Status.PENDING;
    }

}
