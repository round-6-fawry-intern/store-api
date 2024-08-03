package com.fawryEx.storyEx.service;

import com.fawryEx.storyEx.entity.Stock;
import com.fawryEx.storyEx.entity.StockHistory;
import com.fawryEx.storyEx.repository.ProductRepository;
import com.fawryEx.storyEx.repository.StockHistoryRepository;
import com.fawryEx.storyEx.repository.StockRepository;
import com.fawryEx.storyEx.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockHistoryRepository stockHistoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;

    public Stock addStock(Long storeId, Long productId, int quantity) {

        Stock stock = new Stock();
        stock.setStore(storeRepository.findById(storeId).orElse(null));
        stock.setProduct(productRepository.findById(productId).orElse(null));
        stock.setQuantity(quantity);
        stock.setTimestamp(LocalDateTime.now());
        stock.setType("add");


        Stock savedStock = stockRepository.save(stock);


        StockHistory stockHistory = new StockHistory();
        stockHistory.setStore(savedStock.getStore());
        stockHistory.setProduct(savedStock.getProduct());
        stockHistory.setQuantity(quantity);
        stockHistory.setTimestamp(LocalDateTime.now());
        stockHistory.setType("add");
        stockHistoryRepository.save(stockHistory);

        return savedStock;
    }

    public Stock consumeStock(Long storeId, Long productId, int quantity) {

        Stock stock = stockRepository.findByStoreIdAndProductId(storeId, productId)
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        if (stock.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        stock.setQuantity(stock.getQuantity() - quantity);
        stock.setTimestamp(LocalDateTime.now());
        stock.setType("consume");
        Stock updatedStock = stockRepository.save(stock);

        StockHistory stockHistory = new StockHistory();
        stockHistory.setStore(updatedStock.getStore());
        stockHistory.setProduct(updatedStock.getProduct());
        stockHistory.setQuantity(quantity);
        stockHistory.setTimestamp(LocalDateTime.now());
        stockHistory.setType("consume");
        stockHistoryRepository.save(stockHistory);

        return updatedStock;
    }

    public String checkStock(List<Long> storeIds, List<Long> productIds) {
         List<Stock> list =stockRepository.findByStoreIdInAndProductIdIn(storeIds, productIds);
         if(list.isEmpty()){
             return "";
         }else{
             return "";
         }
    }
}

