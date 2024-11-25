package com.oreilly.shopping.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    void countProducts() {
        assertEquals(3, productRepository.count());
    }

    @Test
    void shouldGetAllProducts() {
        assertTrue(productRepository.findAll().size() == 3);
    }

    @Test
    void shoulGetASingleProduct() {
        assertTrue(productRepository.findById(1L).get() != null);
    }
}