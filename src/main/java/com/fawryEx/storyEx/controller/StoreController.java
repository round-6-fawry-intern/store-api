package com.fawryEx.storyEx.controller;


import com.fawryEx.storyEx.DTO.StoreDTO;
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

    @GetMapping
    public ResponseEntity<BaseResponse<List<Store>>> getStores() {
        BaseResponse<List<Store>> baseResponse = new BaseResponse<>();
        baseResponse.setData(storeService.getAllStore());
        return ResponseEntity.ok(baseResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteStore(@PathVariable Long id) {
        BaseResponse<Void> baseResponse = new BaseResponse<>();
        return ResponseEntity.ok(baseResponse);
    }

/////////////////////////////*/////////////////////////////////////////////////////////////////////
    @PostMapping
    public ResponseEntity<BaseResponse<StoreDTO>> createStore(@Valid @RequestBody StoreDTO store) {
        BaseResponse<StoreDTO> baseResponse = new BaseResponse<>();
        baseResponse.setData(storeService.createStore(store));
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<StoreDTO>> getStoreById(@PathVariable Long id) {
        BaseResponse<StoreDTO> baseResponse = new BaseResponse<>();
        baseResponse.setData(storeService.getStoreById(id));
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/products/search")
    public ResponseEntity<BaseResponse<List<Product>>> searchProducts(@RequestParam String name) {
        List<Product> products = storeService.searchProducts(name);
        BaseResponse<List<Product>> baseResponse = new BaseResponse<>();
        baseResponse.setData(products);
        return ResponseEntity.ok(baseResponse);
//        return storeService.searchProducts(name);
    }
}
