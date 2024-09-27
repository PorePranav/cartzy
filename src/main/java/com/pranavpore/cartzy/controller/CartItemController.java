package com.pranavpore.cartzy.controller;

import com.pranavpore.cartzy.exceptions.ResourceNotFoundException;
import com.pranavpore.cartzy.model.Cart;
import com.pranavpore.cartzy.model.User;
import com.pranavpore.cartzy.repository.UserRepository;
import com.pranavpore.cartzy.response.APIResponse;
import com.pranavpore.cartzy.service.cart.ICartItemService;
import com.pranavpore.cartzy.service.cart.ICartService;
import com.pranavpore.cartzy.service.user.UserService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor
@Controller
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/addToCart")
    public ResponseEntity<APIResponse> addItemToCart(@RequestParam Long productId, @RequestParam Integer quantity) {
        try {
            User user = userService.getAuthenticatedUser();
            Cart cart = cartService.initializeNewCart(user);
            cartItemService.addItemToCart(cart.getId(), productId, quantity);
            return ResponseEntity.ok(new APIResponse("success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new APIResponse(e.getMessage(), null));
        } catch (JwtException e) {
            return ResponseEntity.status(UNAUTHORIZED).body(new APIResponse(e.getMessage(), null));
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
