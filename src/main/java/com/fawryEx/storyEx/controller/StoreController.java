package com.fawryEx.storyEx.controller;


import com.fawryEx.storyEx.Exception.base.BaseResponse;
import com.fawryEx.storyEx.entity.Product;
import com.fawryEx.storyEx.entity.Store;
import com.fawryEx.storyEx.service.ProductServiceClient;
import com.fawryEx.storyEx.service.StoreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;

    private ProductServiceClient productServiceClient;

    @PostMapping
    public ResponseEntity<BaseResponse<Store>> createStore(@Valid @RequestBody Store store) {
        BaseResponse<Store> baseResponse = new BaseResponse<>();
        baseResponse.setData(storeService.createStore(store));
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/products")
    public ResponseEntity<BaseResponse<List<Product>>> getProducts() {

        BaseResponse<List<Product>> baseResponse = new BaseResponse<>();
        baseResponse.setData(productServiceClient.getAllProducts());
        return ResponseEntity.ok(baseResponse);

    }
}
