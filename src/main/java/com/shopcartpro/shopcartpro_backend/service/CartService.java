package com.shopcartpro.shopcartpro_backend.service;

import com.shopcartpro.shopcartpro_backend.dto.CartResponse;
import com.shopcartpro.shopcartpro_backend.model.Cart;
import com.shopcartpro.shopcartpro_backend.model.Product;
import com.shopcartpro.shopcartpro_backend.repository.CartRepository;
import com.shopcartpro.shopcartpro_backend.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    public List<CartResponse> getCartByUserId(Long userId){
        return cartRepository.findByUserId(userId).stream().map(cart -> {
            CartResponse response = new CartResponse();
            response.setCartId(cart.getCartId());
            response.setProductId(cart.getProduct().getProductId());
            response.setProductName(cart.getProduct().getName());
            response.setPrice(cart.getProduct().getPrice());
            response.setQuantity(cart.getQuantity());
            response.setTotal(cart.getProduct().getPrice() * cart.getQuantity());
            return response;
        })
                .collect(Collectors.toList());

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

    public Map<String, Object> updateCartQuantity(Long userId, Long productId, int quantity){
        Cart existingCartItem = cartRepository.findByUserIdAndProduct_ProductId(userId,productId);
        if(existingCartItem == null){
            throw new RuntimeException("Cart item not found for userId: " + userId + ", productId: " + productId);
        }
        existingCartItem.setQuantity(quantity);
        Cart updated = cartRepository.save(existingCartItem);

        Map<String, Object> response = new HashMap<>();
        response.put("cartId", updated.getCartId());
        response.put("productId", updated.getProduct().getProductId());
        response.put("quantity", updated.getQuantity());
        response.put("message", "Cart updated successfully");

        return response;
    }

    @Transactional
    public void removeFromCart(Long userId, Long productId){
        cartRepository.deleteByUserIdAndProduct_ProductId(userId,productId);
    }

    public void clearCart(Long userId){
        List<Cart> cartItems = cartRepository.findByUserId(userId);
        cartRepository.deleteAll(cartItems);
    }
}
