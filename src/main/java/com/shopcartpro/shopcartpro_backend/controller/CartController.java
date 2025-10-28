package com.shopcartpro.shopcartpro_backend.controller;

import com.shopcartpro.shopcartpro_backend.dto.CartResponse;
import com.shopcartpro.shopcartpro_backend.model.Cart;
import com.shopcartpro.shopcartpro_backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/getAllCartItems/{userId}")
    public List<CartResponse> getCartByUserId(@PathVariable Long userId) {
        return cartService.getCartByUserId(userId);
    }

    @PostMapping("/addToCart")
    public Cart addToCart(@RequestParam Long userId,
                          @RequestParam Long productId,
                          @RequestParam(defaultValue = "1") int quantity){
        return cartService.addToCart(userId,productId,quantity);
    }

    @PutMapping("/updateCart")
    public Map<String, Object> updateCartQuantity(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam int quantity) {
        return cartService.updateCartQuantity(userId, productId, quantity);
    }

    @DeleteMapping("/removeProduct/{userId}/{productId}")
    public void removeFromCart(@PathVariable Long userId,
                               @PathVariable Long productId) {

        cartService.removeFromCart(userId, productId);
    }

    @DeleteMapping("/removeAll/{userId}")
    public void clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
    }
}
