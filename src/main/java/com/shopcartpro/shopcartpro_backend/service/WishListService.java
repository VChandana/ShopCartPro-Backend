package com.shopcartpro.shopcartpro_backend.service;

import com.shopcartpro.shopcartpro_backend.model.Product;
import com.shopcartpro.shopcartpro_backend.model.WishList;
import com.shopcartpro.shopcartpro_backend.repository.ProductRepository;
import com.shopcartpro.shopcartpro_backend.repository.WishListRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WishListService {

    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    ProductRepository productRepository;

    public List<WishList> getAllWishListItems(Long userId){
        return wishListRepository.findByUserId(userId);
    }

    public WishList addToWishList(Long userId, Long productId){
        if(wishListRepository.existsByUserIdAndProduct_ProductId(userId, productId)){
            throw new RuntimeException("Product is already in WishList");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(()->new RuntimeException("Product not found"));

        WishList wishList = new WishList();
        wishList.setUserId(userId);
        wishList.setProduct(product);
        wishList.setCreatedAt(LocalDateTime.now());
        return wishListRepository.save(wishList);
    }

    @Transactional
    public void removeProduct(Long userId, Long productId){
        wishListRepository.deleteByUserIdAndProduct_ProductId(userId, productId);
    }

    @Transactional
    public void clearWishList(Long userId){
        wishListRepository.deleteByUserId(userId);
    }
}
