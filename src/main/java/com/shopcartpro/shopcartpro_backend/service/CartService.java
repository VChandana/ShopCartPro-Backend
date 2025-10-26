package com.shopcartpro.shopcartpro_backend.service;

import com.shopcartpro.shopcartpro_backend.model.Cart;
import com.shopcartpro.shopcartpro_backend.model.Product;
import com.shopcartpro.shopcartpro_backend.repository.CartRepository;
import com.shopcartpro.shopcartpro_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    public List<Cart> getCartByUserId(Long userId){
        return cartRepository.findByUserId(userId);
    }

    public Cart addToCart(Long userId, Long productId, int quantity){
        Optional<Product> productOptional=  productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new RuntimeException("Product not found with ID: " + productId);
        }
        Product product = productOptional.get();
        Cart existingCartItem = cartRepository.findByUserIdAndProduct_ProductId(userId, productId);
        if(existingCartItem != null){
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            return cartRepository.save(existingCartItem);
        }
        else{
            Cart newCartItem = new Cart(null, userId, product, quantity);
            return cartRepository.save(newCartItem);
        }
    }

    public Cart updateCartQuantity(Long userId, Long productId, int quantity){
        Cart existingCartItem = cartRepository.findByUserIdAndProduct_ProductId(userId,productId);
        if(existingCartItem == null){
            throw new RuntimeException("Cart item not found for userId: " + userId + ", productId: " + productId);
        }
        existingCartItem.setQuantity(quantity);
        return cartRepository.save(existingCartItem);
    }

    public void removeFromCart(Long userId, Long productId){
        cartRepository.deleteByUserIdAndProduct_ProductId(userId,productId);
    }

    public void clearCart(Long userId){
        List<Cart> cartItems = cartRepository.findByUserId(userId);
        cartRepository.deleteAll(cartItems);
    }
}
