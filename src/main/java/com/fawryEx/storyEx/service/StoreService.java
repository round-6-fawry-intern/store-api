package com.fawryEx.storyEx.service;

import com.fawryEx.storyEx.entity.Product;
import com.fawryEx.storyEx.entity.Stock;
import com.fawryEx.storyEx.entity.StockHistory;
import com.fawryEx.storyEx.entity.Store;
import com.fawryEx.storyEx.repository.ProductRepository;
import com.fawryEx.storyEx.repository.StockHistoryRepository;
import com.fawryEx.storyEx.repository.StockRepository;
import com.fawryEx.storyEx.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockHistoryRepository stockHistoryRepository;


    public Store createStore(Store store) {
        return storeRepository.save(store);
    }


    public List<Product> getProducts() {
        return productRepository.findAll();
    }


//    public void addStock(Long storeId, Long productId, int quantity) {
//        Stock stock = new Stock();
//        stock.setStoreId(storeId);
//        stock.setProductId(productId);
//        stock.setQuantity(quantity);
//        stock.setTimestamp(LocalDateTime.now());
//        stock.setType("add");
//        stockRepository.save(stock);
//
//        // سجل في تاريخ المخزون
//        StockHistory history = new StockHistory();
//        history.setStoreId(storeId);
//        history.setProductId(productId);
//        history.setQuantity(quantity);
//        history.setTimestamp(LocalDateTime.now());
//        history.setType("add");
//        stockHistoryRepository.save(history);
//    }
//
//
//    public void consumeProduct(Long storeId, Long productId, int quantity) {
//        Stock stock = new Stock();
//        stock.setStoreId(storeId);
//        stock.setProductId(productId);
//        stock.setQuantity(-quantity);
//        stock.setTimestamp(LocalDateTime.now());
//        stock.setType("consume");
//        stockRepository.save(stock);
//
//        // سجل في تاريخ المخزون
//        StockHistory history = new StockHistory();
//        history.setStoreId(storeId);
//        history.setProductId(productId);
//        history.setQuantity(-quantity);
//        history.setTimestamp(LocalDateTime.now());
//        history.setType("consume");
//        stockHistoryRepository.save(history);
//    }
}
