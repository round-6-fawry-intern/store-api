package com.fawryEx.storyEx.service;

import com.fawryEx.storyEx.Exception.exceptiones.InsufficientStockException;
import com.fawryEx.storyEx.Exception.exceptiones.StoreNotFoundException;
import com.fawryEx.storyEx.entity.Stock;
import com.fawryEx.storyEx.entity.StockHistory;
import com.fawryEx.storyEx.repository.StockHistoryRepository;
import com.fawryEx.storyEx.repository.StockRepository;
import com.fawryEx.storyEx.repository.StoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockService {

    private static final Logger logger = LoggerFactory.getLogger(StockService.class);

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockHistoryRepository stockHistoryRepository;

//    @Autowired
//    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;

    public Stock addStock(Long storeId, Long productId, int quantity) {
        logger.info("Adding stock: Store ID = {}, Product ID = {}, Quantity = {}", storeId, productId, quantity);
        Stock stock = new Stock();
        stock.setStore(storeId);
        stock.setProduct(productId);
        stock.setQuantity(quantity);
        stock.setTimestamp(LocalDateTime.now());
        stock.setType("add");

        Stock savedStock = stockRepository.save(stock);
        logger.info("Stock added successfully: {}", savedStock);


        StockHistory stockHistory = new StockHistory();
        stockHistory.setStore(storeRepository.findById(storeId).get());
        stockHistory.setProduct(productId);
        stockHistory.setQuantity(quantity);
        stockHistory.setTimestamp(LocalDateTime.now());
        stockHistory.setType("add");
        stockHistoryRepository.save(stockHistory);
        logger.info("Stock history created successfully: {}", stockHistory);
        return savedStock;
    }

    public Stock consumeStock(Long storeId, Long productId, int quantity) {
        logger.info("Consuming stock: Store ID = {}, Product ID = {}, Quantity = {}", storeId, productId, quantity);
        Stock stock = stockRepository.findByStoreIdAndProductId(storeId, productId)
                .orElseThrow(() -> new StoreNotFoundException("Stock not found"));
//        logger.error("Stock not found for Store ID = {}, Product ID = {}", storeId, productId);

        if (stock.getQuantity() < quantity) {
            logger.error("Insufficient stock: Store ID = {}, Product ID = {}, Available Quantity = {}, Requested Quantity = {}", storeId, productId, stock.getQuantity(), quantity);
            throw new InsufficientStockException("Insufficient stock");
        }

        stock.setQuantity(stock.getQuantity() - quantity);
        stock.setTimestamp(LocalDateTime.now());
        stock.setType("consume");
        Stock updatedStock = stockRepository.save(stock);
        logger.info("Stock consumed successfully: {}", updatedStock);

        StockHistory stockHistory = new StockHistory();
        stockHistory.setStore(storeRepository.findById(storeId).get());
        stockHistory.setProduct(updatedStock.getProduct());
        stockHistory.setQuantity(quantity);
        stockHistory.setTimestamp(LocalDateTime.now());
        stockHistory.setType("consume");
        stockHistoryRepository.save(stockHistory);
        logger.info("Stock history created successfully: {}", stockHistory);

        return updatedStock;
    }

    public String checkStock(List<Long> storeIds, List<Long> productIds) {
        logger.info("Checking stock: Store IDs = {}, Product IDs = {}", storeIds, productIds);
        List<Stock> list =stockRepository.findByStoreIdInAndProductIdIn(storeIds, productIds);
         if(list.isEmpty()){
             logger.info("No stock found for the given store IDs and product IDs.");
             return "No stock found for the given store IDs and product IDs.";
         }else{
             logger.info("Stock found: {}", list);
             return "Stock found";
         }
    }

}

