package com.shopcartpro.shopcartpro_backend.controller;

import com.shopcartpro.shopcartpro_backend.dto.OrderResponse;
import com.shopcartpro.shopcartpro_backend.model.Order;
import com.shopcartpro.shopcartpro_backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/placeOrder")
    public OrderResponse placeOrder(@RequestParam Long userId){
        return orderService.placeOrder(userId);
    }

    @GetMapping("/getAllOrders/{userId}")
    public List<OrderResponse> getAllOrders(@PathVariable Long userId){
        return orderService.getOrdersByUser(userId);
    }

    @GetMapping("/getOrderById/{orderId}")
    public OrderResponse getOrderById( @PathVariable Long orderId){
        return orderService.getOrdersByOrderId(orderId);
    }
}
