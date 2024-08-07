package com.fawryEx.storyEx.controller;

import com.fawryEx.storyEx.Exception.base.BaseResponse;
import com.fawryEx.storyEx.entity.Stock;
import com.fawryEx.storyEx.service.StockService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping("/add")
    public ResponseEntity<BaseResponse<Stock>> addStock(@RequestParam Long storeId, @RequestParam Long productId, @RequestParam int quantity) {
        BaseResponse<Stock> baseResponse = new BaseResponse<>();
        baseResponse.setData(stockService.addStock(storeId, productId, quantity));
        return ResponseEntity.ok(baseResponse);
//        return stockService.addStock(storeId, productId, quantity);
    }

    @PostMapping
    public Stock addStock(@RequestBody Stock stock) {
        return stockService.addStock(stock);
    }

    @GetMapping("/store/{storeId}")
    public List<Stock> getStockByStore(@PathVariable Long storeId) {
        return stockService.getStockByStore(storeId);
    }

    @GetMapping("/product/{productId}")
    public List<Stock> getStockByProduct(@PathVariable Long productId) {
        return stockService.getStockByProduct(productId);
    }

    @PostMapping("/consumeDate")
    public void consumeProduct(@RequestParam Long productId, @RequestParam Long storeId, @RequestParam int quantity, @RequestParam String date) {
        LocalDate consumptionDate = LocalDate.parse(date);
        stockService.consumeProduct(productId, storeId, quantity, consumptionDate);
    }

    @PostMapping("/consume")
    public ResponseEntity<BaseResponse<Stock>> consumeStock(@Valid @RequestParam Long storeId, @RequestParam Long productId, @RequestParam int quantity) {
        BaseResponse<Stock> baseResponse = new BaseResponse<>();
        baseResponse.setData(stockService.consumeStock(storeId, productId, quantity));
        return ResponseEntity.ok(baseResponse);
//        return stockService.consumeStock(storeId, productId, quantity);
    }

    @PostMapping("/check")
    public ResponseEntity<BaseResponse<String>> checkStock(@RequestBody List<StockRequest> stockRequests) {
        List<Long> storeIds = stockRequests.stream().map(StockRequest::getStoreId).distinct().toList();
        List<Long> productIds = stockRequests.stream().map(StockRequest::getProductId).distinct().toList();
        String result = stockService.checkStock(storeIds, productIds);
        BaseResponse<String> baseResponse = new BaseResponse<>();
        baseResponse.setData(result);
        return ResponseEntity.ok(baseResponse);
//        List<Long> storeIds = stockRequests.stream().map(StockRequest::getStoreId).distinct().toList();
//        List<Long> productIds = stockRequests.stream().map(StockRequest::getProductId).distinct().toList();
//        return stockService.checkStock(storeIds, productIds);
    }

    public static class StockRequest {
        private Long storeId;
        private Long productId;

        public Long getStoreId() { return storeId; }
        public void setStoreId(Long storeId) { this.storeId = storeId; }

        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
    }
}

