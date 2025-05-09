package com.CareNet.CN.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "patients")
public class Patient {
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
    private String email;

    @Setter
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    public Patient() {
    }

    public Patient(String name, String email) {
        this.name = name;
        this.email = email;
        this.status = Status.PENDING;
    }

}
