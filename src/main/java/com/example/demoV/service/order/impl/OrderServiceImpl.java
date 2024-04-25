package com.example.demoV.service.order.impl;

import com.example.demoV.base.BaseResponse;
import com.example.demoV.common.MessageCommon;
import com.example.demoV.dto.OrderDetailDto;
import com.example.demoV.dto.OrderedProductsDto;
import com.example.demoV.exception.BusinessException;
import com.example.demoV.exception.NotFoundException;
import com.example.demoV.model.Order;
import com.example.demoV.model.OrderDetail;
import com.example.demoV.repository.OrderDetailRepository;
import com.example.demoV.repository.OrderRepository;
import com.example.demoV.repository.ProductRepository;
import com.example.demoV.service.order.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("OrderService")
public class OrderServiceImpl extends BaseResponse implements OrderService {
    private final OrderRepository orderRepository;
    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, OrderDetailRepository orderDetailRepository, ObjectMapper objectMapper) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public ResponseEntity<?> getList() {
        return getResponse(orderRepository.findAll());
    }

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ResponseEntity<?> save(Order order) {
        return getResponse(orderRepository.save(order));
    }

    private final OrderDetailRepository orderDetailRepository;

    @Transactional
    @Override
    public ResponseEntity<?> save(OrderDetailDto dto) {
        List<OrderedProductsDto> list = dto.getOrderDetail();
        Order order = new Order(dto.getOrderId(), dto.getCustomerName(), dto.getOrderDate()
                , dto.getEmail(), dto.getNumberPhone(), dto.getPay()
                , dto.getStatus());
        //kiểm tra đơn hàng tồn tại hay chưa trước khi tạo mới hoặc update
        boolean check = orderRepository.existsByOrOrderId(dto.getOrderId());
        order = orderRepository.save(order);
        // nếu status là final thi không cần cập nhập sản phẩm
        if (!order.getStatus().equals(MessageCommon.ORDER_FINAL)) {
            List<OrderDetail> orderDetails = new ArrayList<>();
            //check = true thì sẽ là cần update
            if (check) {
                //trước khi update sản phẩm trong đơn hàng cần trả lại hàng về kho
                List<OrderDetail> orderDetailList = orderDetailRepository.findAllByOrderId(dto.getOrderId());
                orderDetailList.forEach(e -> {
                    int iQty = productRepository.getIQtyByPid(e.getProductId());
                    iQty = iQty + e.getQuantity();
                    productRepository.updateIQty(e.getProductId(), iQty);
                });
                // nếu không phải hủy đơn thì mới xóa sản phẩm trong order detail để insert lại
                if (!dto.getStatus().equals(MessageCommon.ORDER_CANCEL)) {
                    orderDetailRepository.deleteAllByOrderId(dto.getOrderId());
                }
            }
            //Nếu là CANCEL mọi thứ chỉ cần giữ nguyên không cần sửa
            if (!dto.getStatus().equals(MessageCommon.ORDER_CANCEL)) {
                for (OrderedProductsDto e : list
                ) {
                    int iQty = productRepository.getIQtyByPid(e.getProductId());
                    if (iQty < e.getQty()) {
                        throw new BusinessException(MessageCommon.QTY_EXCEEDED);
                    } else {
                        iQty = iQty - e.getQty();
                        productRepository.updateIQty(e.getProductId(), iQty);

                    }
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrderId(order.getOrderId());
                    orderDetail.setProductId(e.getProductId());
                    orderDetail.setQuantity(e.getQty());
                    orderDetails.add(orderDetail);
                }
            }
            orderDetailRepository.saveAll(orderDetails);
        }


        return getResponse(order);
    }


    @Override
    public ResponseEntity<?> delete(int id) {
        orderRepository.deleteById(id);
        return getResponse("success");
    }

    private final ObjectMapper objectMapper;

    @Override
    public ResponseEntity<?> searchAllByCustomerOrOrderId(String cname, int id) {
        return getResponse(orderRepository.searchAllByOrderIdOrCustomerName(id,cname));
    }

    private OrderDetailDto getOrderDetailDto(Order order) {
        OrderDetailDto dto = objectMapper.convertValue(order, OrderDetailDto.class);
        List<Map<String, Objects>> mapList = orderRepository.getOrderDetail(order.getOrderId());
        List<OrderedProductsDto> orderDetailDtoList = new ArrayList<>();
        for (Map<String, Objects> m :
                mapList) {
            OrderedProductsDto o = objectMapper.convertValue(m, OrderedProductsDto.class);
            orderDetailDtoList.add(o);
        }
        dto.setOrderDetail(orderDetailDtoList);
        return dto;
    }

    @Override
    public ResponseEntity<?> getOrderById(int id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isEmpty()) {
            throw new NotFoundException(MessageCommon.ORDER_NOT_FOUND);
        }
        return getResponse(getOrderDetailDto(optionalOrder.get()));
    }


}
