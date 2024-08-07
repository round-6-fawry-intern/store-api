package com.fawryEx.storyEx.service;

import com.fawryEx.storyEx.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${product.api.url}")
    private String productApiUrl;

    public Product getProduct(Long productId) {
        return restTemplate.getForObject(productApiUrl + "/" + productId, Product.class);
    }

    public List<Product> searchProducts(String name) {
        // Implement search functionality if available
        // For example, you might have an endpoint like `/search?name={name}`
        return restTemplate.getForObject(productApiUrl + "/search?name=" + name, List.class);
    }
}

