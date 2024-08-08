package com.fawryEx.storyEx.service;

import com.fawryEx.storyEx.DTO.StockRequestModel;
import com.fawryEx.storyEx.Exception.exceptiones.ProductException;

import com.fawryEx.storyEx.entity.Product;
import com.fawryEx.storyEx.entity.Stock;
import com.fawryEx.storyEx.repository.StockRepository;
import com.fawryEx.storyEx.repository.StoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {

  private static final Logger logger = LoggerFactory.getLogger(StockService.class);

  @Autowired private StockRepository stockRepository;

  @Autowired private ProductServiceClient productServiceClient;

  @Autowired private StoreRepository storeRepository;

  public Stock addStock(Long storeId, Long productId, int quantity) {
    logger.info(
        "Adding stock: Store ID = {}, Product ID = {}, Quantity = {}",
        storeId,
        productId,
        quantity);
    Stock stock = new Stock();
    stock.setStoreId(storeId);
    stock.setProductId(productId);
    stock.setQuantity(quantity);
    stock.setTimestamp(LocalDateTime.now());
    stock.setType("add");

    Stock savedStock = stockRepository.save(stock);
    logger.info("Stock added successfully: {}", savedStock);
    return savedStock;
  }


  @Transactional
  public void consumeStock(List<StockRequestModel>stockRequests) {
    List<Stock> stocks = validateItems(stockRequests); // n
    int i = 0;
    for (Stock stock : stocks) {

      stock.setQuantity(stock.getQuantity() - stockRequests.get(i).getQuantity());
      i++;

      stockRepository.save(stock);
    }
  }

  public String checkStock(List<StockRequestModel> stockRequests) {

    validateItems(stockRequests);
    return "valid productIds";
  }

  public Stock addStock(Stock stock) {

    Product product = productServiceClient.getProduct(stock.getProductId());
    if (product == null) {
      throw new RuntimeException("Product not found");
    }
    return stockRepository.save(stock);
  }

  public List<Stock> getStockByStore(Long storeId) {
    return stockRepository.findByStoreId(storeId);
  }

  public List<Stock> getStockByProduct(Long productId) {
    return stockRepository.findByProductId(productId);
  }

  public void consumeProduct(List<StockRequestModel> stockRequests) {

    List<Stock> stocks = validateItems(stockRequests); // n
    int i = 0;
    for (Stock stock : stocks) {

      stock.setQuantity(stock.getQuantity() - stockRequests.get(i).getQuantity());
      i++;
    }
  }

  private List<Stock> validateItems(List<StockRequestModel> stockRequests) {
    List<String> producterrors = new ArrayList<>();

    List<Stock> stocks = new ArrayList<>();

    for (StockRequestModel stockRequestModel : stockRequests) {

      System.out.println(stockRequests.getFirst().toString());
      Stock optionalStock =
          stockRepository
              .findByStoreIdAndProductId(
                  stockRequestModel.getStoreId(), stockRequestModel.getProductId())
              .orElseGet(
                  () -> {
                    producterrors.add(
                        "Product with ID " + stockRequestModel.getProductId() + " not found");
                    return null;
                  });

      System.out.println(optionalStock);
      if (optionalStock !=null && optionalStock.getQuantity() < stockRequestModel.getQuantity()) {
        producterrors.add(
            "Product with ID "
                + stockRequestModel.getProductId()
                + " it is available just "
                + optionalStock.getQuantity());
      }

      stocks.add(optionalStock);
    }

    if (!producterrors.isEmpty()) {
      throw new ProductException(producterrors);
    }

    return stocks;
  }
}
