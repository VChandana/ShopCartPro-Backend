package com.shopcartpro.shopcartpro_backend.repository;

import com.shopcartpro.shopcartpro_backend.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    List<Cart> findByUserId(Long userId);

    Cart findByUserIdAndProduct_ProductId(Long userId, Long productId);  // ✅ productId (entity field name)

    void deleteByUserIdAndProduct_ProductId(Long userId, Long productId);

    void deleteByUserId(Long userId);

}
