package com.fawryEx.storyEx.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.fawryEx.storyEx.Exception.exceptiones.InsufficientStockException;
import com.fawryEx.storyEx.Exception.exceptiones.StoreNotFoundException;
import com.fawryEx.storyEx.entity.Product;
import com.fawryEx.storyEx.entity.Stock;
import com.fawryEx.storyEx.entity.StockHistory;
import com.fawryEx.storyEx.entity.Store;
import com.fawryEx.storyEx.repository.ProductRepository;
import com.fawryEx.storyEx.repository.StockHistoryRepository;
import com.fawryEx.storyEx.repository.StockRepository;
import com.fawryEx.storyEx.repository.StoreRepository;
import com.fawryEx.storyEx.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

public class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private StockHistoryRepository stockHistoryRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StockService stockService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddStock() {
        // إعداد البيانات الاختبارية
        Long storeId = 1L;
        Long productId = 1L;
        int quantity = 10;

        Stock stock = new Stock();
        stock.setStore(new Store());
        stock.setProduct(new Product());
        stock.setQuantity(quantity);
        stock.setTimestamp(LocalDateTime.now());
        stock.setType("add");

        when(storeRepository.findById(storeId)).thenReturn(Optional.of(stock.getStore()));
        when(productRepository.findById(productId)).thenReturn(Optional.of(stock.getProduct()));
        when(stockRepository.save(any(Stock.class))).thenReturn(stock);
        when(stockHistoryRepository.save(any(StockHistory.class))).thenReturn(new StockHistory());

        Stock result = stockService.addStock(storeId, productId, quantity);

        assertNotNull(result);
        assertEquals(quantity, result.getQuantity());
        verify(stockRepository).save(any(Stock.class));
        verify(stockHistoryRepository).save(any(StockHistory.class));
    }

    @Test
    public void testConsumeStock() {
        Long storeId = 1L;
        Long productId = 1L;
        int quantity = 5;

        Stock existingStock = new Stock();
        existingStock.setStore(new Store());
        existingStock.setProduct(new Product());
        existingStock.setQuantity(10);
        existingStock.setTimestamp(LocalDateTime.now());
        existingStock.setType("add");

        Stock updatedStock = new Stock();
        updatedStock.setStore(existingStock.getStore());
        updatedStock.setProduct(existingStock.getProduct());
        updatedStock.setQuantity(existingStock.getQuantity() - quantity);
        updatedStock.setTimestamp(LocalDateTime.now());
        updatedStock.setType("consume");

        when(stockRepository.findByStoreIdAndProductId(storeId, productId))
                .thenReturn(Optional.of(existingStock));
        when(stockRepository.save(any(Stock.class))).thenReturn(updatedStock);
        when(stockHistoryRepository.save(any(StockHistory.class))).thenReturn(new StockHistory());

        Stock result = stockService.consumeStock(storeId, productId, quantity);

        assertNotNull(result);
        assertEquals(existingStock.getQuantity() - quantity, result.getQuantity());
        verify(stockRepository).save(any(Stock.class));
        verify(stockHistoryRepository).save(any(StockHistory.class));
    }

    @Test
    public void testConsumeStockInsufficientQuantity() {
        Long storeId = 1L;
        Long productId = 1L;
        int quantity = 15;

        Stock existingStock = new Stock();
        existingStock.setStore(new Store());
        existingStock.setProduct(new Product());
        existingStock.setQuantity(10);

        when(stockRepository.findByStoreIdAndProductId(storeId, productId))
                .thenReturn(Optional.of(existingStock));

        assertThrows(InsufficientStockException.class, () -> {
            stockService.consumeStock(storeId, productId, quantity);
        });
    }
}
