package com.example.demoV.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
    private String productName;
    private String description;
    @Column(columnDefinition = "DECIMAL(20,2)")
    private BigDecimal price;
    private int inventoryQty;

}
