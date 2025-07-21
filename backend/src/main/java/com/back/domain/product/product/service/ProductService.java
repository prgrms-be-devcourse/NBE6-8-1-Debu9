package com.back.domain.product.product.service;

import com.back.domain.product.product.entity.Product;
import com.back.domain.product.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public long count() {
        return productRepository.count();
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    public void modify(Product product, String name, String imageUrl, String info, int price, String engName) {
        product.modify(name, imageUrl, info, price, engName);
    }

    public Product write(String name, String imageUrl, String info, int price, String engName) {
        Product product = new Product(name, imageUrl, info, price, engName);

        return productRepository.save(product);
    }
    public Product create(String name, String imageUrl, String info, int price, String engName) {
        Product product = new Product(name, imageUrl, info, price, engName);

        return productRepository.save(product);
    }

    public void delete(Product product) {
        productRepository.delete(product);
    }
}
