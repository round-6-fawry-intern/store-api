package com.fawryEx.storyEx.service;

import com.fawryEx.storyEx.DTO.StoreDTO;
import com.fawryEx.storyEx.Exception.exceptiones.StoreNotFoundException;
import com.fawryEx.storyEx.entity.Product;
import com.fawryEx.storyEx.entity.Store;
//import com.fawryEx.storyEx.repository.ProductRepository;
//import com.fawryEx.storyEx.repository.StockRepository;
import com.fawryEx.storyEx.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ProductServiceClient productServiceClient;

    @Transactional
    public StoreDTO createStore(StoreDTO storeDTO) {
        Store store = new Store();
        store.setName(storeDTO.getName());
        store.setLocation(storeDTO.getLocation());
        store.setEmail(storeDTO.getEmail());
        store = storeRepository.save(store);
        return convertToDTO(store);
    }


//    public List<Product> getProducts() {
//        return productRepository.findAll();
//    }

    public StoreDTO getStoreById(Long id) {
        return storeRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new StoreNotFoundException("Store not found"));
    }

    @Transactional
    public void deleteStore(Long id) {
        if (!storeRepository.existsById(id)) {
            throw new StoreNotFoundException("the id = " + id + " not found");
        }
        storeRepository.deleteById(id);
    }

    public List<Store> getAllStore() {
       return storeRepository.findAll();
    }

    public List<Product> searchProducts(String name) {
        return productServiceClient.searchProducts(name);
    }

    private StoreDTO convertToDTO(Store store) {
        StoreDTO dto = new StoreDTO();
        dto.setId(store.getId());
        dto.setName(store.getName());
        dto.setLocation(store.getLocation());
        dto.setEmail(store.getEmail());
        return dto;
    }
}
