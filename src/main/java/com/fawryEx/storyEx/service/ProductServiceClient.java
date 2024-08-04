package com.fawryEx.storyEx.service;

import com.fawryEx.storyEx.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${product.api.url}")
    private String productApiUrl;

    public List<Product> getAllProducts() {
        String url = String.format("%s/products", productApiUrl);
        return restTemplate.getForObject(url, List.class);
    }
}

