package com.pranavpore.cartzy.data;

import com.pranavpore.cartzy.model.User;
import com.pranavpore.cartzy.model.Role;
import com.pranavpore.cartzy.repository.RoleRepository;
import com.pranavpore.cartzy.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Transactional
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
        createDefaultRoleIfNotExists(defaultRoles);
        createDefaultUsersIfNotExists();
        createDefaultAdminIfNotExists();
    }

    private void createDefaultUsersIfNotExists() {
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        for (int i = 0; i < 5; i++) {
            String defaultEmail = "user" + (i + 1) + "@pranavpore.com";
            if (!userRepository.existsByEmail(defaultEmail)) {
                User user = new User();
                user.setFirstName("User " + (i + 1));
                user.setLastName("User " + (i + 1));
                user.setEmail(defaultEmail);
                user.setPassword(passwordEncoder.encode("123456"));
                user.setRoles(Set.of(userRole));
                userRepository.save(user);
                System.out.println("User " + i + " created");
            }
        }
    }
    private void createDefaultAdminIfNotExists() {
        Role userRole = roleRepository.findByName("ROLE_ADMIN").get();
        for (int i = 0; i < 2; i++) {
            String defaultEmail = "admin" + (i + 1) + "@pranavpore.com";
            if (!userRepository.existsByEmail(defaultEmail)) {
                User user = new User();
                user.setFirstName("Admin");
                user.setLastName("Admin");
                user.setEmail(defaultEmail);
                user.setRoles(Set.of(userRole));
                user.setPassword(passwordEncoder.encode("123456"));
                userRepository.save(user);
                System.out.println("Admin created");
            }
        }
    }
    private void createDefaultRoleIfNotExists(Set<String> roles) {
        roles.stream().filter(role -> roleRepository
                .findByName(role)
                .isEmpty())
                .map(Role::new)
                .forEach(roleRepository::save);
    }
}
