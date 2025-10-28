package com.shopcartpro.shopcartpro_backend.controller;

import com.shopcartpro.shopcartpro_backend.model.WishList;
import com.shopcartpro.shopcartpro_backend.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/wishlist")
public class WishListController {

    @Autowired
    WishListService wishListService;

    @GetMapping("getAllWishListItems/{userId}")
    public List<WishList> getAllWishListItems(@PathVariable Long userId){
        return wishListService.getAllWishListItems(userId);
    }

    @PostMapping("addToWishList")
    public WishList addToWishList(@RequestParam Long userId,
                                  @RequestParam Long productId){
        return wishListService.addToWishList(userId, productId);
    }

    @DeleteMapping("removeProduct/{userId}/{productId}")
    public void removeProduct(@PathVariable Long userId, @PathVariable Long productId){
        wishListService.removeProduct(userId, productId);
    }

    @DeleteMapping("/clearWishList/{userId}")
    public void clearWishList(@PathVariable Long userId){
        wishListService.clearWishList(userId);
    }
}
