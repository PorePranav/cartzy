package com.pranavpore.cartzy.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDTO {
    private Long id;
    private String productName;
    private int quantity;
    private BigDecimal price;
}
