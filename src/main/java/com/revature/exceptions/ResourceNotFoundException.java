package com.revature.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("No resource was found using the provided search criteria.");
    }

}
