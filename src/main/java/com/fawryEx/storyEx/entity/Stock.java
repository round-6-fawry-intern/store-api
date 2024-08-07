package com.fawryEx.storyEx.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long storeId;
    private Long productId;

    @Min(0)
    private int quantity;
    private LocalDateTime timestamp;
    private String type;

}
