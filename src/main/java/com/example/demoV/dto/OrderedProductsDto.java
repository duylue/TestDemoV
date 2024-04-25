package com.example.demoV.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderedProductsDto {
    private int productId;
    private String productName;
    private String description;
    private BigDecimal price;
    private int inventoryQty;
    private int qty;
}
