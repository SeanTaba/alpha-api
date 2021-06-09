package com.revature.exceptions;

public class DataSourceException extends RuntimeException{
    public DataSourceException(){
        super("There was a problem when communicating with the database");
    }
    public DataSourceException(String message) {
        super(message);
    }
}

