package com.oreilly.shopping.services;

import com.oreilly.shopping.entities.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductServiceTest {

    @Autowired
    WebTestClient testClient;
    @Autowired
    JdbcTemplate jdbcTemplate;

    private List<Long> getIds() {
        return jdbcTemplate.queryForList("select id from products", Long.class);
    }

    @Test
    void shouldFindAllProducts() {
        testClient.get()
                .uri("/products")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class).hasSize(getIds().size());
    }

    @ParameterizedTest
    @MethodSource("getIds")
    void findProductById(Long id) {
        testClient.get()
                .uri("/products/{id}", id)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(id);
    }

    @Test
    void insertProduct() {
        Product product = new Product("Chair", BigDecimal.valueOf(49.99));
        testClient.post()
                .uri("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(product), Product.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.name").isEqualTo("Chair")
                .jsonPath("$.price").isEqualTo(49.99);
    }

    @Test
    void updateProduct() {
        Product product = getProduct(getIds().get(0));
        testClient.put()
                .uri("/products/{id}", product.getId())
                .body(Mono.just(product), Product.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .consumeWith(System.out::println);
    }

    @Test
    void deleteSingleProduct() {
        List<Long> ids = getIds();
        if(ids.size() == 0) {
            System.out.println("There are no products in the database");
            return;
        }
        testClient.get()
                .uri("/products")
                .exchange()
                .expectStatus().isOk();

        testClient.delete()
                .uri("/products/{id}", ids.get(0))
                .exchange()
                .expectStatus().isNoContent();

        testClient.get()
                .uri("/products/{id}", ids.get(0))
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void deleteAllProducts() {
        testClient.delete()
                        .uri("/products")
                                .exchange()
                                        .expectStatus()
                                                .isNoContent();
        testClient.get()
                .uri("/products")
                .exchange()
                .expectStatus().isNoContent();
    }
    private Product getProduct(Long id) {
        List<Product> products = jdbcTemplate.queryForList("select * from products", Product.class);
        return products.get(id.intValue());
    }

}