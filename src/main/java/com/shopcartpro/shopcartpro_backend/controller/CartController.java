package com.shopcartpro.shopcartpro_backend.controller;

import com.shopcartpro.shopcartpro_backend.model.Cart;
import com.shopcartpro.shopcartpro_backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {
    @Autowired
    private CartService cartService;

    // ✅ Get all cart items for a user
    @GetMapping("/{userId}")
    public List<Cart> getCartByUserId(@PathVariable Long userId) {
        return cartService.getCartByUserId(userId);
    }
}
