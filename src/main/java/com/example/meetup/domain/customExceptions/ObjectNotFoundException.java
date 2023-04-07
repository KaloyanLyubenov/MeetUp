package com.example.meetup.domain.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Object was not found")
public class ObjectNotFoundException  extends RuntimeException{
    private String identifier;
    private String identifierType;
    private String objectType;

    public ObjectNotFoundException(String identifier, String identifierType, String objectType){
        super(objectType + " with " + identifierType + ": " + identifier + " was not found!");
        this.objectType = objectType;
        this.identifier = identifier;
        this.identifierType = identifierType;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getIdentifierType() {
        return identifierType;
    }

    public String getObjectType() {
        return objectType;
    }
}
