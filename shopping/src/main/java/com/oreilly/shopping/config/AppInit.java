package com.oreilly.shopping.config;

import com.oreilly.shopping.entities.Product;
import com.oreilly.shopping.repositories.ProductRepository;
import com.oreilly.shopping.services.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class AppInit implements CommandLineRunner {
    private final ProductService productService;

    public AppInit(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void run(String... args) {
        productService.initializeDatabase();
    }
}
