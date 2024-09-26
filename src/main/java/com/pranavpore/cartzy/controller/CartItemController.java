package com.pranavpore.cartzy.controller;

import com.pranavpore.cartzy.exceptions.ResourceNotFoundException;
import com.pranavpore.cartzy.response.APIResponse;
import com.pranavpore.cartzy.service.cart.ICartItemService;
import com.pranavpore.cartzy.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Controller
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;

    @PostMapping("/addToCart")
    public ResponseEntity<APIResponse> addItemToCart(@RequestParam(required = false) Long cartId,
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity) {
        try {
            if (cartId == null)
                cartId = cartService.initializeNewCart();
            cartItemService.addItemToCart(cartId, productId, quantity);
            return ResponseEntity.ok(new APIResponse("success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new APIResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/removeItem/{cartId}/item/{productId}")
    public ResponseEntity<APIResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
        try {
           cartItemService.removeItemFromCart(cartId, productId);
           return ResponseEntity.ok(new APIResponse("success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/updateQuantity/{cartId}/item/{productId}")
    public ResponseEntity<APIResponse> updateItemQuantity(@PathVariable Long cartId,
                                                          @PathVariable Long productId,
                                                          @RequestParam Integer quantity) {
        try {
            cartItemService.updateItemQuantity(cartId, productId, quantity);
            return ResponseEntity.ok(new APIResponse("success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }
}
