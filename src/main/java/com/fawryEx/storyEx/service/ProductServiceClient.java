package com.fawryEx.storyEx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${product.api.url}")
    private String productApiUrl;

//    public Product getAllProducts(Long id) {
//        String url = String.format("%s/products/", productApiUrl);
//        ResponseEntity<Product> post= restTemplate.getForEntity(url+id,Product.class);
//        return post.getBody();
//    }
}

