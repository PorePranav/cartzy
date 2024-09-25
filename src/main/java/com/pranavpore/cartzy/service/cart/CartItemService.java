package com.pranavpore.cartzy.service.cart;

import com.pranavpore.cartzy.exceptions.ResourceNotFoundException;
import com.pranavpore.cartzy.model.Cart;
import com.pranavpore.cartzy.model.CartItem;
import com.pranavpore.cartzy.model.Product;
import com.pranavpore.cartzy.repository.CartItemRepository;
import com.pranavpore.cartzy.repository.CartRepository;
import com.pranavpore.cartzy.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ICartService cartService;
    private final IProductService productService;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart
                .getCartItems()
                .stream()
                .filter(item -> item
                        .getProduct()
                        .getId()
                        .equals(productId))
                .findFirst()
                .orElse(new CartItem());
        if (cartItem.getId() == null) {
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setCart(cart);
            cartItem.setUnitPrice(product.getPrice());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        cartItem.setTotalPrice();
        cart.addCartItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(productId, cart);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        cart.getCartItems()
            .stream().filter(item -> item
                .getId()
                .equals(productId))
            .findFirst()
            .ifPresent(item -> {
                item.setQuantity(quantity);
                item.setUnitPrice(productService.getProductById(productId).getPrice());
                item.setTotalPrice();
            });
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long productId, Cart cart) {
        return cart
                .getCartItems()
                .stream()
                .filter(item -> item
                        .getProduct()
                        .getId()
                        .equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }}
