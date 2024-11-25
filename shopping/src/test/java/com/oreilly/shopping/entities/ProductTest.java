package com.oreilly.shopping.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductTest {
    @Autowired
    Validator validator;

    @Test
    void validProduct () {
        Product product = new Product("Pan", BigDecimal.valueOf(10));
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
    }

    @Test
    void invalidProduct () {
        Product product = new Product(1L," ", BigDecimal.valueOf(-10));
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertEquals(3, violations.size());
        violations.forEach(System.out::println);
    }

}