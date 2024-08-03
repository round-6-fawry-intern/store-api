package com.fawryEx.storyEx.controller;

import com.fawryEx.storyEx.entity.Stock;
import com.fawryEx.storyEx.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping("/add")
    public Stock addStock(@RequestParam Long storeId, @RequestParam Long productId, @RequestParam int quantity) {
        return stockService.addStock(storeId, productId, quantity);
    }

    @PostMapping("/consume")
    public Stock consumeStock(@RequestParam Long storeId, @RequestParam Long productId, @RequestParam int quantity) {
        return stockService.consumeStock(storeId, productId, quantity);
    }

    @PostMapping("/check")
    public String checkStock(@RequestBody List<StockRequest> stockRequests) {
        // تحويل قائمة من StockRequest إلى قائمة من storeId و productId
        List<Long> storeIds = stockRequests.stream().map(StockRequest::getStoreId).distinct().toList();
        List<Long> productIds = stockRequests.stream().map(StockRequest::getProductId).distinct().toList();
        return stockService.checkStock(storeIds, productIds);
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

