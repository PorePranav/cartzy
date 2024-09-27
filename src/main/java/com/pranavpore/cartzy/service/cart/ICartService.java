package com.pranavpore.cartzy.service.cart;

import com.pranavpore.cartzy.model.Cart;
import com.pranavpore.cartzy.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    Cart initializeNewCart(User user);
    Cart getCartByUserId(Long id);
}
