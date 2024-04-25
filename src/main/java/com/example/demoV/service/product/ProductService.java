package com.example.demoV.service.product;

import com.example.demoV.model.Product;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    ResponseEntity<?> getList();
    ResponseEntity<?> save(Product product);
    ResponseEntity<?> delete(int id);
    ResponseEntity<?> getProductById(int id);
    ResponseEntity<?> searchAllByProductName(String name);
    ResponseEntity<?> searchAllByDescription(String des);
    ResponseEntity<?> searchAllByDescriptionOrProductName(String des, String pName);
}
