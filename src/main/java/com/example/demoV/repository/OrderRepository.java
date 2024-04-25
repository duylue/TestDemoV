package com.example.demoV.repository;

import com.example.demoV.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query(nativeQuery = true, value = "select" +
            " od.quantity as qty," +
            " p.product_name as productName, " +
            "       p.product_id as productId ," +
            "       p.price, " +
            "       p.description, " +
            "       p.inventory_qty as inventoryQty " +
            "from product p, order_detail od " +
            "where p.product_id = od.product_id " +
            "and od.order_id = ?")
    List<Map<String, Objects>> getOrderDetail(int oid);

    boolean existsByOrOrderId(int oid);
    List<Order> searchAllByOrderIdOrCustomerName(int oid, String cname);
}
