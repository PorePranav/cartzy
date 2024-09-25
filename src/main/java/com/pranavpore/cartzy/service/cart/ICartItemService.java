package com.pranavpore.cartzy.service.cart;

import com.pranavpore.cartzy.model.Cart;
import com.pranavpore.cartzy.model.CartItem;

public interface ICartItemService {
    public void addItemToCart(Long cartId, Long productId, int quantity);
    public void removeItemFromCart(Long cartId, Long productId);
    public void updateItemQuantity(Long cartId, Long productId, int quantity);

    CartItem getCartItem(Long productId, Cart cart);
}
