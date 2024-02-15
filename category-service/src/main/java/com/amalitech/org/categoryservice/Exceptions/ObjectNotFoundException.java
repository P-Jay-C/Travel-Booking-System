package com.amalitech.org.categoryservice.Exceptions;

public class ObjectNotFoundException extends RuntimeException{
    public ObjectNotFoundException(String objectName, String id) {
        super("Could not find "+ objectName+ " with id: "+id + " :(");
    }

    public ObjectNotFoundException(String objectName, Long id) {
        super("Could not find "+ objectName+ " with id: "+id + " :(");
    }

}
