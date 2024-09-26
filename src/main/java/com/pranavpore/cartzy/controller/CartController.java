package com.pranavpore.cartzy.controller;

import com.pranavpore.cartzy.exceptions.ResourceNotFoundException;
import com.pranavpore.cartzy.model.Cart;
import com.pranavpore.cartzy.response.APIResponse;
import com.pranavpore.cartzy.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Controller
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final ICartService cartService;

    @GetMapping("/{cartId}")
    public ResponseEntity<APIResponse> getCart(@PathVariable Long cartId) {
        try {
            Cart cart = cartService.getCart(cartId);
            return ResponseEntity.ok(new APIResponse("success", cart));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new APIResponse("no cart with id " + cartId + " found", null));
        }
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<APIResponse> clearCart(@PathVariable Long cartId) {
        try {
           Cart cart = cartService.getCart(cartId);
           cartService.clearCart(cartId);
           return ResponseEntity.ok(new APIResponse("success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new APIResponse("no cart with id " + cartId + " found", null));
        }
    }

    @GetMapping("/{cartId}/totalAmount")
    public ResponseEntity<APIResponse> getTotalAmount(@PathVariable Long cartId) {
        try {
            Cart cart = cartService.getCart(cartId);
            return ResponseEntity.ok(new APIResponse("success", cart.getTotalAmount()));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse("no cart with id " + cartId + " found", null));
        }
    }
}
