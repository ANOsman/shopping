package com.oreilly.shopping.controllers;

import com.oreilly.shopping.entities.Product;
import jakarta.validation.ConstraintViolation;

import java.io.StringBufferInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InvalidProductException extends RuntimeException {
    private Map<String, Object> properties = new HashMap<>();

    public InvalidProductException() {
        this("Invalid Product");
    }
    public InvalidProductException(String message) {
        super(message);
    }
    public InvalidProductException(Set<ConstraintViolation<Product>> violations) {
        this("Invalid Product");
        for (ConstraintViolation<Product> violation:
             violations) {
            properties.put(violation.getMessage(), violation.getInvalidValue());
        }
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

}
