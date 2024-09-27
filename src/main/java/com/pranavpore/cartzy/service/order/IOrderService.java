package com.pranavpore.cartzy.service.order;

import com.pranavpore.cartzy.dto.OrderDTO;
import com.pranavpore.cartzy.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDTO getOrder(Long userId);

    List<OrderDTO> getUserOrders(Long userId);
}
