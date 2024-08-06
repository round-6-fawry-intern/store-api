package com.fawryEx.storyEx.controller;


import com.fawryEx.storyEx.Exception.base.BaseResponse;
import com.fawryEx.storyEx.entity.Store;
import com.fawryEx.storyEx.service.ProductServiceClient;
import com.fawryEx.storyEx.service.StoreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<BaseResponse<Store>> getStoreById(@Valid @RequestBody Long id) {
        BaseResponse<Store> baseResponse = new BaseResponse<>();
        baseResponse.setData(storeService.getStoreById(id));
        return ResponseEntity.ok(baseResponse);
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<Void>> deleteStore(@Valid @RequestBody Long id) {
        BaseResponse<Void> baseResponse = new BaseResponse<>();
        return ResponseEntity.ok(baseResponse);
    }
/*
    @GetMapping("/products")
    public ResponseEntity<BaseResponse<Product>> getProducts(@RequestBody Long id) {

        BaseResponse<Product> baseResponse = new BaseResponse<>();
        baseResponse.setData(productServiceClient.getAllProducts(id));
        return ResponseEntity.ok(baseResponse);

    }*/
}
