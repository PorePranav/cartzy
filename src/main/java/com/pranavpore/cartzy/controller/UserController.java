package com.pranavpore.cartzy.controller;

import com.pranavpore.cartzy.exceptions.ResourceAlreadyExistsException;
import com.pranavpore.cartzy.exceptions.ResourceNotFoundException;
import com.pranavpore.cartzy.model.User;
import com.pranavpore.cartzy.request.CreateUserRequest;
import com.pranavpore.cartzy.request.UpdateUserRequest;
import com.pranavpore.cartzy.response.APIResponse;
import com.pranavpore.cartzy.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<APIResponse> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            return ResponseEntity.ok(new APIResponse("success", userService.convertToDTO(user)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new APIResponse("No user with id " + userId + " found", null));
        }
    }

    @PostMapping("/")
    public ResponseEntity<APIResponse> createUser(@RequestBody CreateUserRequest request) {
        try {
            User user = userService.createUser(request);
            return ResponseEntity.ok(new APIResponse("success", user));
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new APIResponse("User already exists", null));
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<APIResponse> updateUser(@PathVariable Long userId, @RequestBody UpdateUserRequest request) {
        try {
            User updatedUser = userService.updateUser(request, userId);
            return ResponseEntity.ok(new APIResponse("success", userService.convertToDTO(updatedUser)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse("User not found", null));
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<APIResponse> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new APIResponse("success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse("User not found", null));
        }
    }
}
