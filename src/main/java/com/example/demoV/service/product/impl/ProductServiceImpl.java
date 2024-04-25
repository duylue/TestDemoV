package com.example.demoV.service.product.impl;

import com.example.demoV.base.BaseResponse;
import com.example.demoV.common.MessageCommon;
import com.example.demoV.exception.NotFoundException;
import com.example.demoV.model.Product;
import com.example.demoV.repository.ProductRepository;
import com.example.demoV.service.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("productService")
public class ProductServiceImpl extends BaseResponse implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<?> getList() {
        return getResponse(productRepository.findAll());
    }

    @Override
    public ResponseEntity<?> save(Product product) {
        return getResponse(productRepository.save(product));
    }

    @Override
    public ResponseEntity<?> delete(int id) {
        productRepository.deleteById(id);
        return getResponse(MessageCommon.SUCCESS);
    }

    @Override
    public ResponseEntity<?> getProductById(int id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new NotFoundException(MessageCommon.PRODUCT_NOT_FOUND);
        }
        return getResponse(optionalProduct.get());

    }

    @Override
    public ResponseEntity<?> searchAllByDescriptionOrProductName(String des, String pName) {
        List<Product> products = productRepository.searchAllByDescriptionOrProductName(des, pName);
        if (products != null && products.isEmpty()) {
            return getResponse(productRepository.searchAllByDescriptionOrProductName(des, pName));
        }
        throw new NotFoundException(MessageCommon.PRODUCT_NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> searchAllByDescription(String des) {
        return getResponse(productRepository.searchAllByDescription(des));
    }

    @Override
    public ResponseEntity<?> searchAllByProductName(String name) {
        return getResponse(productRepository.searchAllByProductName(name));
    }
}
