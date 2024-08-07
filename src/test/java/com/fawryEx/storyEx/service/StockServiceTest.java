package com.fawryEx.storyEx.service;

import com.fawryEx.storyEx.Exception.exceptiones.InsufficientStockException;
import com.fawryEx.storyEx.Exception.exceptiones.StoreNotFoundException;
import com.fawryEx.storyEx.entity.Product;
import com.fawryEx.storyEx.entity.Stock;
import com.fawryEx.storyEx.entity.Store;
import com.fawryEx.storyEx.repository.StockRepository;
import com.fawryEx.storyEx.repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private ProductServiceClient productServiceClient;

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StockService stockService;

    @Test
    public void testAddStock_Success() {
        Stock stock = new Stock();
        stock.setStoreId(1L);
        stock.setProductId(1L);
        stock.setQuantity(10);
        stock.setTimestamp(LocalDateTime.now());
        stock.setType(Stock.StockType.ADD);

        Product product = new Product();
        product.setId(1L);

        Store store = new Store();
        store.setId(1L);
        store.setName("Test Store");
        store.setLocation("Test Location");
        store.setEmail("test@example.com");

        when(productServiceClient.getProduct(1L)).thenReturn(product);
        when(storeRepository.existsById(1L)).thenReturn(true);
        when(stockRepository.save(any(Stock.class))).thenReturn(stock);

        Stock result = stockService.addStock(1L, 1L, 10);

        assertNotNull(result);
        assertEquals(10, result.getQuantity());
        assertEquals(1L, result.getStoreId());
        assertEquals(1L, result.getProductId());
        assertEquals(Stock.StockType.ADD, result.getType());
        verify(stockRepository).save(any(Stock.class));
    }

    @Test
    public void testConsumeStock_InsufficientStock() {
        Stock stock = new Stock();
        stock.setStoreId(1L);
        stock.setProductId(1L);
        stock.setQuantity(5);  // Quantity is less than the requested

        when(stockRepository.findByStoreIdAndProductId(1L, 1L)).thenReturn(Optional.of(stock));

        InsufficientStockException thrown = assertThrows(InsufficientStockException.class, () -> {
            stockService.consumeStock(1L, 1L, 10);  // Requesting more than available
        });

        assertEquals("Insufficient stock", thrown.getMessage());
        verify(stockRepository, never()).save(any(Stock.class));
    }

    @Test
    public void testConsumeStock_Success() {
        Stock stock = new Stock();
        stock.setStoreId(1L);
        stock.setProductId(1L);
        stock.setQuantity(20);

        when(stockRepository.findByStoreIdAndProductId(1L, 1L)).thenReturn(Optional.of(stock));
        when(stockRepository.save(any(Stock.class))).thenReturn(stock);

        Stock result = stockService.consumeStock(1L, 1L, 10);

        assertNotNull(result);
        assertEquals(10, result.getQuantity());
        verify(stockRepository).save(any(Stock.class));
    }

    @Test
    public void testCheckStock_Found() {
        Stock stock = new Stock();
        stock.setStoreId(1L);
        stock.setProductId(1L);

        when(stockRepository.findByStoreIdInAndProductIdIn(List.of(1L), List.of(1L)))
                .thenReturn(List.of(stock));

        String result = stockService.checkStock(List.of(1L), List.of(1L));

        assertEquals("Stock found", result);
    }

    @Test
    public void testCheckStock_NotFound() {
        when(stockRepository.findByStoreIdInAndProductIdIn(List.of(1L), List.of(1L)))
                .thenReturn(List.of());

        String result = stockService.checkStock(List.of(1L), List.of(1L));

        assertEquals("No stock found for the given store IDs and product IDs.", result);
    }

    @Test
    public void testConsumeProduct_Success() {
        Stock stock = new Stock();
        stock.setStoreId(1L);
        stock.setProductId(1L);
        stock.setQuantity(20);
        stock.setTimestamp(LocalDate.of(2024, 8, 7).atStartOfDay());

        when(stockRepository.findByStoreId(1L)).thenReturn(List.of(stock));
        when(stockRepository.save(any(Stock.class))).thenReturn(stock);

        stockService.consumeProduct(1L, 1L, 10, LocalDate.of(2024, 8, 7));

        verify(stockRepository).save(any(Stock.class));
    }

    @Test
    public void testConsumeProduct_InsufficientStock() {
        Stock stock = new Stock();
        stock.setStoreId(1L);
        stock.setProductId(1L);
        stock.setQuantity(5);
        stock.setTimestamp(LocalDateTime.of(2024, 8, 7, 0, 0)); // إضافة قيمة للتوقيت

        when(stockRepository.findByStoreId(1L)).thenReturn(List.of(stock));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            stockService.consumeProduct(1L, 1L, 10, LocalDate.of(2024, 8, 7));
        });

        assertEquals("Not enough stock available", thrown.getMessage());
        verify(stockRepository, never()).save(any(Stock.class));
    }
}
