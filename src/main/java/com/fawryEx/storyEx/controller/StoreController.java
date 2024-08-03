package com.fawryEx.storyEx.controller;


import com.fawryEx.storyEx.entity.Product;
import com.fawryEx.storyEx.entity.Store;
import com.fawryEx.storyEx.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @PostMapping
    public Store createStore(@RequestBody Store store) {
        return storeService.createStore(store);
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return storeService.getProducts();
    }

//    @PostMapping("/{storeId}/stock")
//    public void addStock(@PathVariable Long storeId, @RequestParam Long productId, @RequestParam int quantity) {
//        storeService.addStock(storeId, productId, quantity);
//    }
//
//    @PostMapping("/{storeId}/consume")
//    public void consumeProduct(@PathVariable Long storeId, @RequestParam Long productId, @RequestParam int quantity) {
//        storeService.consumeProduct(storeId, productId, quantity);
//    }
}
