package com.pranavpore.cartzy.service.cart;

import com.pranavpore.cartzy.model.Cart;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Long initializeNewCart();
    Cart getCartByUserId(Long id);
}
