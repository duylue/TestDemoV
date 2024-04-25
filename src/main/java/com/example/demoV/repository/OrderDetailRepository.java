package com.example.demoV.repository;

import com.example.demoV.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    void deleteAllByOrderId(int oid);
    List<OrderDetail> findAllByOrderId(int oid);
}
