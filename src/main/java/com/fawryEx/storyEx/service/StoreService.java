package com.fawryEx.storyEx.service;

import com.fawryEx.storyEx.entity.Product;
import com.fawryEx.storyEx.entity.Store;
import com.fawryEx.storyEx.repository.ProductRepository;
import com.fawryEx.storyEx.repository.StockHistoryRepository;
import com.fawryEx.storyEx.repository.StockRepository;
import com.fawryEx.storyEx.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockHistoryRepository stockHistoryRepository;


    public Store createStore(Store store) {
        return storeRepository.save(store);
    }


    public List<Product> getProducts() {
        return productRepository.findAll();
    }

}
