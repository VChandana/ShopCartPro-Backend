package com.shopcartpro.shopcartpro_backend.service;

import com.shopcartpro.shopcartpro_backend.dto.OrderItemResponse;
import com.shopcartpro.shopcartpro_backend.dto.OrderResponse;
import com.shopcartpro.shopcartpro_backend.model.Cart;
import com.shopcartpro.shopcartpro_backend.model.Order;
import com.shopcartpro.shopcartpro_backend.model.OrderItem;
import com.shopcartpro.shopcartpro_backend.model.Product;
import com.shopcartpro.shopcartpro_backend.repository.CartRepository;
import com.shopcartpro.shopcartpro_backend.repository.OrderItemRepository;
import com.shopcartpro.shopcartpro_backend.repository.OrderRepository;
import com.shopcartpro.shopcartpro_backend.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    //When user clicks “Place Order” (from frontend/cart):
    //Fetch all cart items of that user
    //Create a new Order object
    //For each cart item → create OrderItem
    //Calculate total amount
    //Save order (cascade will save items automatically)
    //Clear the cart

    @Transactional
    public OrderResponse placeOrder(Long userId){
        List<Cart> cartItems = cartRepository.findByUserId(userId);
        if(cartItems.isEmpty()){
            throw new RuntimeException("Cart is empty, cannot place order!");
        }
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderStatus("PENDING");

        List<OrderItem> orderItemList = new ArrayList<>();
        double totalAmount = 0;

        for(Cart cart : cartItems){
            Product product = cart.getProduct();

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setOrder(order);
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setPrice(cart.getQuantity()*product.getPrice());
            orderItemList.add(orderItem);
            totalAmount += orderItem.getPrice();
        }
        order.setTotalAmount(totalAmount);
        order.setOrderItems(orderItemList);

        Order savedOrder = orderRepository.save(order);
        cartRepository.deleteByUserId(userId);
        return toOrderResponse(savedOrder);
    }

    private OrderResponse toOrderResponse(Order order){
        OrderResponse dto = new OrderResponse();
        dto.setOrderId(order.getOrderId());
        dto.setUserId(order.getUserId());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setTotalAmount(order.getTotalAmount());

        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        if(order.getOrderItems()!=null){
            orderItemResponses = order.getOrderItems().stream()
                    .map(item->(toOrderItemResponse(item)))
                    .collect(Collectors.toList());
        }
        dto.setOrderItems(orderItemResponses);
        return dto;
    }

    private OrderItemResponse toOrderItemResponse(OrderItem orderItem){
        OrderItemResponse dto = new OrderItemResponse();
        dto.setOrderItemId(orderItem.getOrderItemId());
        if(orderItem.getProduct() != null){
            dto.setProductId(orderItem.getProduct().getProductId());
            dto.setProductName(orderItem.getProduct().getName());
            dto.setProductPrice(orderItem.getProduct().getPrice());
        }
        dto.setQuantity(orderItem.getQuantity());
        dto.setLineTotal(orderItem.getPrice());
        return dto;
    }

    public List<OrderResponse> getOrdersByUser(Long userId){
        return orderRepository.findByUserId(userId)
                .stream()
                .map(item->toOrderResponse(item))
                .collect(Collectors.toList());
    }

    public OrderResponse getOrdersByOrderId(Long orderId){
        Order order = orderRepository.findById(orderId).
                orElseThrow(()->new RuntimeException("Order not found"));
        return toOrderResponse(order);
    }
}


/*
 * ❌ ISSUE: Infinite JSON recursion (stack overflow / 3000+ line response)
 *
 * When we returned Order entities directly, each Order contained a list of OrderItems,
 * and each OrderItem had a reference back to its Order.
 *
 * This caused Jackson (JSON serializer) to go in an infinite loop:
 * Order → OrderItem → Order → OrderItem → ...
 *
 * ⚠️ Error seen:
 * "Document nesting depth (1001) exceeds the maximum allowed (1000)"
 *
 * ✅ FIX: Introduced DTOs (OrderResponse, OrderItemResponse)
 * to break circular references and return only the necessary fields to the frontend.
 *
 * Now controllers return clean, finite JSON responses — no recursion, no hibernate proxy issues.
 */
