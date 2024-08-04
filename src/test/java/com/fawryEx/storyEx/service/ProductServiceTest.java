package com.fawryEx.storyEx.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fawryEx.storyEx.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class ProductServiceTest {

//    @Mock
//    private RestTemplate restTemplate;
//
//    @InjectMocks
//    private ProductService productService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testGetAllProducts() {
//        Product product1 = new Product(1L, "Product1", 100);
//        Product product2 = new Product(2L, "Product2", 200);
//        List<Product> products = Arrays.asList(product1, product2);
//
//        String url = "http://localhost:8080/api/products";
//        when(restTemplate.getForObject(url, List.class)).thenReturn(products);
//
//        List<Product> result = productService.getAllProducts();
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        assertEquals("Product1", result.get(0).getName());
//        assertEquals("Product2", result.get(1).getName());
//        verify(restTemplate).getForObject(url, List.class);
//    }
}
