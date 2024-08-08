package com.fawryEx.storyEx.service;

import com.fawryEx.storyEx.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ProductServiceClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ProductServiceClient productServiceClient;

    @Value("${product.api.url}")
    private String productApiUrl = "http://localhost:8080/products"; // تعيين قيمة افتراضية

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProduct_Success() {
        // إعداد البيانات
        Product product = new Product();
        product.setId(1L);

        // تعيين قيمة productApiUrl يدويًا
        productServiceClient.productApiUrl = "http://localhost:5050/products";

        // إعداد سلوك موك RestTemplate
        when(restTemplate.getForObject(any(String.class), eq(Product.class))).thenReturn(product);

        // تنفيذ الاختبار
        Product result = productServiceClient.getProduct(1L);

        // التحقق من النتائج
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(restTemplate).getForObject(any(String.class), eq(Product.class));
    }

    @Test
    public void testGetProduct_Failure() {
        // تعيين قيمة productApiUrl يدويًا
        productServiceClient.productApiUrl = "http://localhost:5050/products";

        // إعداد سلوك موك RestTemplate لإلقاء استثناء
        when(restTemplate.getForObject(any(String.class), eq(Product.class)))
                .thenThrow(new RuntimeException("Service unavailable"));

        // تنفيذ الاختبار والتحقق من الاستثناء
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            productServiceClient.getProduct(1L);
        });

        assertEquals("Error retrieving product", thrown.getMessage());
        verify(restTemplate).getForObject(any(String.class), eq(Product.class));
    }

    @Test
    public void testSearchProducts_Success() {
        // إعداد البيانات
        Product product = new Product();
        product.setId(1L);

        // إعداد سلوك موك RestTemplate
        ResponseEntity<List> responseEntity = new ResponseEntity<>(Collections.singletonList(product), HttpStatus.OK);
        when(restTemplate.getForEntity(any(String.class), eq(List.class))).thenReturn(responseEntity);

        // تعيين قيمة productApiUrl في ProductServiceClient
        productServiceClient.productApiUrl = "http://localhost:8080/products"; // تعيين قيمة افتراضية

        // تنفيذ الاختبار
        List<Product> result = productServiceClient.searchProducts("test");

        // التحقق من النتائج
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        verify(restTemplate).getForEntity(any(String.class), eq(List.class));
    }

    @Test
    public void testSearchProducts_Failure() {
        // إعداد البيانات
        Product product = new Product();
        product.setId(1L);

        // إعداد سلوك موك RestTemplate لإلقاء استثناء
        when(restTemplate.getForEntity(any(String.class), eq(List.class)))
                .thenThrow(new RuntimeException("Service unavailable"));

        // تعيين قيمة productApiUrl في ProductServiceClient
        productServiceClient.productApiUrl = "http://localhost:5050/products"; // تعيين قيمة افتراضية

        // تنفيذ الاختبار والتحقق من الاستثناء
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            productServiceClient.searchProducts("test");
        });

        assertEquals("Error occurred while searching for products", thrown.getMessage());
        verify(restTemplate).getForEntity(any(String.class), eq(List.class));
    }
}
