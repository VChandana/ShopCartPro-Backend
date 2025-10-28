package com.shopcartpro.shopcartpro_backend.repository;

import com.shopcartpro.shopcartpro_backend.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {

    List<WishList> findByUserId(Long userId);

    Optional<WishList> findByUserIdAndProduct_ProductId(Long userId, Long productId);

    void deleteByUserIdAndProduct_ProductId(Long userId, Long productId);

    void deleteByUserId(Long userId);

    boolean existsByUserIdAndProduct_ProductId(Long userId, Long productId);
}
