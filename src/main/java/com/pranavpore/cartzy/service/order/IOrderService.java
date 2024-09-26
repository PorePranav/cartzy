package com.pranavpore.cartzy.service.order;

import com.pranavpore.cartzy.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    Order getOrder(Long userId);

    List<Order> getUserOrders(Long userId);
}
