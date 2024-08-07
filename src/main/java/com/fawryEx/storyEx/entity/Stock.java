package com.fawryEx.storyEx.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Stock {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private Long storeId;
//    private Long productId;
//
//    @Min(0)
//    private int quantity;
//    private LocalDateTime timestamp;
//    private String type;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "store_id", nullable = false)
    private Long storeId;

//    @ManyToOne
//    @JoinColumn(name = "product_id", nullable = false)
    private Long productId;

    @Min(value = 0, message = "Quantity must be greater than or equal to zero")
    private int quantity;

    private LocalDateTime timestamp;

    @NotBlank(message = "Type cannot be blank")
    @Enumerated(EnumType.STRING)
    private StockType type;

    // Enum to represent stock types
    public enum StockType {
        ADD,
        CONSUME
    }
}
