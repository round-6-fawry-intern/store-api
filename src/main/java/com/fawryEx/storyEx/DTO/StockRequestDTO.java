package com.fawryEx.storyEx.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockRequestDTO {
    private Integer quantity;
    private String addedBy;
    private String date;

    // Getters and setters
}
