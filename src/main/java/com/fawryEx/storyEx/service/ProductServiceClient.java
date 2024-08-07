package com.fawryEx.storyEx.service;

import com.fawryEx.storyEx.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class ProductServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${product.api.url}")
    public String productApiUrl;

    public Product getProduct(Long productId) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(productApiUrl)
                    .pathSegment(productId.toString())
                    .toUriString();

            // Print the URL for debugging
            System.out.println("Constructed URL: " + url);

            return restTemplate.getForObject(url, Product.class);
        } catch (Exception e) {
            // Handle exceptions and log errors
            throw new RuntimeException("Error retrieving product", e);
        }
//        return restTemplate.getForObject(productApiUrl + "/" + productId, Product.class);
    }

    public List<Product> searchProducts(String name) {
        String url = UriComponentsBuilder.fromHttpUrl(productApiUrl)
                .pathSegment("search")
                .queryParam("name", name)
                .toUriString();

        try {
            ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                // You may need to convert List<Object> to List<Product> if necessary
                return response.getBody();
            } else {
                // Handle non-success responses
                throw new RuntimeException("Failed to search products: " + response.getStatusCode());
            }
        } catch (Exception e) {
            // Log the error and throw a custom exception if needed
            throw new RuntimeException("Error occurred while searching for products", e);
        }
        // Implement search functionality if available
        // For example, you might have an endpoint like `/search?name={name}`
//        return restTemplate.getForObject(productApiUrl + "/search?name=" + name, List.class);
    }
}

