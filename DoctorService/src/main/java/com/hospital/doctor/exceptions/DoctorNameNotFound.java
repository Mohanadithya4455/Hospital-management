package com.hospital.doctor.exceptions;



public class DoctorNameNotFound extends RuntimeException {
    public DoctorNameNotFound(String message) {
        super(message);
    }
}

