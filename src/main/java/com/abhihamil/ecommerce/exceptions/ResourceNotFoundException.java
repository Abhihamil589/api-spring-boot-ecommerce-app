package com.abhihamil.ecommerce.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    String resourceName;
    String fieldName;
    Long fieldId;

    ResourceNotFoundException(){}

    public ResourceNotFoundException(String resourceName, String fieldName, Long fieldId) {
        super(String.format("Resource %s not found with %d: %s", resourceName, fieldId, fieldName));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldId = fieldId;
    }

    public ResourceNotFoundException(String resourceName, String fieldName) {
        super(String.format("Resource %s not found with %s", resourceName, fieldName));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
    }

}
