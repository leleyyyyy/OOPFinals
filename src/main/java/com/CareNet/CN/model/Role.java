package com.CareNet.CN.model;

public enum Role {
    DOCTOR(1),
    PATIENT(2);


    private final int id;

    Role(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

