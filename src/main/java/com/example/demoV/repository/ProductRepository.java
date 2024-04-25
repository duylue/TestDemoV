package com.example.demoV.repository;

import com.example.demoV.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> searchAllByDescription(String description);

    List<Product> searchAllByProductName(String name);

    List<Product> searchAllByDescriptionOrProductName(String description, String productName);

    @Query(nativeQuery = true, value = "SELECT INVENTORY_QTY FROM PRODUCT WHERE PRODUCT_ID = ?")
    int getIQtyByPid(int pid);

    @Query(nativeQuery = true, value = "UPDATE PRODUCT SET INVENTORY_QTY = :qty  WHERE PRODUCT_ID = :id")
    @Modifying
    void updateIQty(@Param("id") int pid, @Param("qty") int qty);

}
