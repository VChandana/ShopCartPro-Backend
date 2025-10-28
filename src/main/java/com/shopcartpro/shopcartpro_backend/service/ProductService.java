package com.shopcartpro.shopcartpro_backend.service;

import com.shopcartpro.shopcartpro_backend.model.Product;
import com.shopcartpro.shopcartpro_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long productId){
        return productRepository.findById(productId);
    }

    public Product addProduct(Product product){
        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, Product productDetails){
        Product product = productRepository.findById(productId).orElseThrow(
                ()-> new RuntimeException("Product not found with productId : "+productId)
        );
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setImageUrl(productDetails.getImageUrl());
        product.setCategory(productDetails.getCategory());

        return productRepository.save(product);
    }

    public void deleteProduct(Long productId){
        productRepository.deleteById(productId);
    }

    public List<Product> getProductsByCategory(String category){
        return productRepository.findByCategoryIgnoreCase(category);
    }
}
