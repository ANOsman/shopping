package com.oreilly.shopping.controllers;

import com.oreilly.shopping.entities.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductRestControllerTest {

    @Autowired
    WebTestClient testClient;

    @Test
    void shouldGetProductsWithMinPrice() {
        testClient.get()
                .uri("/products?min=5")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void shouldReturnResponseWithBadRequestWhenGivenInvalidPrice() {
        testClient.get()
                .uri("/products?min=-1")
                .exchange()
                .expectStatus().isBadRequest();
    }

}