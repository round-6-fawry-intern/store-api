package com.fawryEx.storyEx.service;

import com.fawryEx.storyEx.DTO.StoreDTO;
import com.fawryEx.storyEx.Exception.exceptiones.StoreNotFoundException;
import com.fawryEx.storyEx.entity.Product;
import com.fawryEx.storyEx.entity.Store;
import com.fawryEx.storyEx.repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private ProductServiceClient productServiceClient;

    @InjectMocks
    private StoreService storeService;

    @Test
    public void testCreateStore() {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setName("Test Store");
        storeDTO.setLocation("Test Location");
        storeDTO.setEmail("test@example.com");

        Store store = new Store();
        store.setId(1L);
        store.setName("Test Store");
        store.setLocation("Test Location");
        store.setEmail("test@example.com");

        when(storeRepository.save(any(Store.class))).thenReturn(store);

        StoreDTO createdStoreDTO = storeService.createStore(storeDTO);

        assertNotNull(createdStoreDTO);
        assertEquals(storeDTO.getName(), createdStoreDTO.getName());
        assertEquals(storeDTO.getLocation(), createdStoreDTO.getLocation());
        assertEquals(storeDTO.getEmail(), createdStoreDTO.getEmail());
    }

    @Test
    public void testGetStoreById_Success() {
        Store store = new Store();
        store.setId(1L);
        store.setName("Test Store");
        store.setLocation("Test Location");
        store.setEmail("test@example.com");

        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));

        StoreDTO storeDTO = storeService.getStoreById(1L);

        assertNotNull(storeDTO);
        assertEquals(store.getId(), storeDTO.getId());
        assertEquals(store.getName(), storeDTO.getName());
    }

    @Test
    public void testGetStoreById_NotFound() {
        when(storeRepository.findById(1L)).thenReturn(Optional.empty());

        StoreNotFoundException thrown = assertThrows(StoreNotFoundException.class, () -> {
            storeService.getStoreById(1L);
        });

        assertEquals("Store not found", thrown.getMessage());
    }

    @Test
    @Transactional
    public void testDeleteStore_Success() {
        when(storeRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> storeService.deleteStore(1L));

        verify(storeRepository).deleteById(1L);
    }

    @Test
    @Transactional
    public void testDeleteStore_NotFound() {
        when(storeRepository.existsById(1L)).thenReturn(false);

        StoreNotFoundException thrown = assertThrows(StoreNotFoundException.class, () -> {
            storeService.deleteStore(1L);
        });

        assertEquals("the id = 1 not found", thrown.getMessage());
    }

    @Test
    public void testGetAllStore() {
        Store store1 = new Store();
        store1.setId(1L);
        store1.setName("Store1");

        Store store2 = new Store();
        store2.setId(2L);
        store2.setName("Store2");

        when(storeRepository.findAll()).thenReturn(List.of(store1, store2));

        List<Store> stores = storeService.getAllStore();

        assertNotNull(stores);
        assertEquals(2, stores.size());
    }

    @Test
    public void testSearchProducts() {
        // Mock implementation for ProductServiceClient
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        when(productServiceClient.searchProducts("Test")).thenReturn(List.of(product));

        List<Product> products = storeService.searchProducts("Test");

        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals("Test Product", products.get(0).getName());
    }
}
