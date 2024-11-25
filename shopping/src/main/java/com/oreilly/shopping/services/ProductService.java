package com.oreilly.shopping.services;

import com.oreilly.shopping.entities.Product;
import com.oreilly.shopping.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    public void initializeDatabase() {
        if (productRepository.count() == 0) {
            productRepository.saveAll(
                    List.of(
                            new Product("TV tray", BigDecimal.valueOf(4.95)),
                            new Product("Toaster", BigDecimal.valueOf(19.95)),
                            new Product("Sofa", BigDecimal.valueOf(249.95))
                    )).forEach(System.out::println);
        }
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> findAllProductsByMinPrice(double minPrice) {
        return productRepository.findAllByPriceGreaterThanEqual(BigDecimal.valueOf(minPrice));
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public void deleteAllProducts() {
        productRepository.deleteAllInBatch();
    }
}
