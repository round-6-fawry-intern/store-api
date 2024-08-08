package com.fawryEx.storyEx.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockRequestModel {
  private Long storeId;
  private Long productId;
  private int quantity;
}
