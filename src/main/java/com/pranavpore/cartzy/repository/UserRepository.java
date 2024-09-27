package com.pranavpore.cartzy.repository;

import com.pranavpore.cartzy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    User getUserById(Long id);
    User findByEmail(String email);
}
