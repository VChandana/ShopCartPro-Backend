package com.shopcartpro.shopcartpro_backend.repository;

import com.shopcartpro.shopcartpro_backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}
