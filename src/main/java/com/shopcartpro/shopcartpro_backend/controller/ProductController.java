package com.shopcartpro.shopcartpro_backend.controller;

import com.shopcartpro.shopcartpro_backend.model.Product;
import com.shopcartpro.shopcartpro_backend.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository repo;

    public ProductController(ProductRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Product> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public Product create(@RequestBody Product p) {
        return repo.save(p);
    }
}
