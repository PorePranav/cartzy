package com.pranavpore.cartzy.service.user;

import com.pranavpore.cartzy.dto.UserDTO;
import com.pranavpore.cartzy.model.User;
import com.pranavpore.cartzy.request.CreateUserRequest;
import com.pranavpore.cartzy.request.UpdateUserRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request, Long userId);
    void deleteUser(Long userId);
    UserDTO convertToDTO(User user);
    User getAuthenticatedUser();
}
