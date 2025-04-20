package com.hospital.doctor.exceptions;

public class NotAllowedException extends Exception{
    String message;
    public NotAllowedException(String message){
        super(message);
    }
}
