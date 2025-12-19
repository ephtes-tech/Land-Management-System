package com.land.parcel.exception;

public class InvalidGeometryException extends RuntimeException{
    public InvalidGeometryException(String message){
        super(message);
    }
}
