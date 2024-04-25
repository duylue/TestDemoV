package com.example.demoV.service.order;
import com.example.demoV.dto.OrderDetailDto;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    ResponseEntity<?> getList();

    ResponseEntity<?> save(OrderDetailDto order);

    ResponseEntity<?> getOrderById(int id);

    ResponseEntity<?> searchAllByCustomerOrOrderId(String cname, int id);

}
