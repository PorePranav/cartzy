package com.pranavpore.cartzy.controller;

import com.pranavpore.cartzy.dto.OrderDTO;
import com.pranavpore.cartzy.exceptions.ResourceNotFoundException;
import com.pranavpore.cartzy.model.Order;
import com.pranavpore.cartzy.response.APIResponse;
import com.pranavpore.cartzy.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/placeOrder/{userId}")
    public ResponseEntity<APIResponse> createOrder(@PathVariable Long userId) {
        try {
            Order order = orderService.placeOrder(userId);
            return ResponseEntity.ok(new APIResponse("success", orderService.getOrder(order.getId())));
        } catch (Exception e) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new APIResponse("Error occured!", e.getMessage()));
        }
    }

    @GetMapping("/orderId/{orderId}")
    public ResponseEntity<APIResponse> getOrderById(@PathVariable  Long orderId) {
        try {
            OrderDTO order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new APIResponse("success", order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new APIResponse("Order not found", e.getMessage()));
        }
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<APIResponse> getOrderByUserId(@PathVariable  Long userId) {
        try {
            List<OrderDTO> orders = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new APIResponse("success", orders));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new APIResponse("No user with id " + userId + " found", e.getMessage()));
        }
    }
}
