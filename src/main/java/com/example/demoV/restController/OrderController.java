package com.example.demoV.restController;

import com.example.demoV.dto.OrderDetailDto;
import com.example.demoV.service.order.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@CrossOrigin("*")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;

    }

    @GetMapping
    public ResponseEntity<?> getList() {
        return orderService.getList();
    }

    @GetMapping("/order-detail")
    public ResponseEntity<?> getOrderDetail(@RequestParam int oid) {
        return orderService.getOrderById(oid);
    }

    @GetMapping("/search-order-by-cname-orderId")
    public ResponseEntity<?> delete(@RequestParam int oid, @RequestParam String cname) {
        return orderService.searchAllByCustomerOrOrderId(cname, oid);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody OrderDetailDto order) {
        return orderService.save(order);
    }

}
