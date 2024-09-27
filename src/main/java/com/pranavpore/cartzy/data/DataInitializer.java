package com.pranavpore.cartzy.data;

import com.pranavpore.cartzy.model.User;
import com.pranavpore.cartzy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createDefaultUsersIfNotExists();
    }

    private void createDefaultUsersIfNotExists() {
        for (int i = 0; i < 5; i++) {
            String defaultEmail = "user" + (i + 1) + "@pranavpore.com";
            if (!userRepository.existsByEmail(defaultEmail)) {
                User user = new User();
                user.setFirstName("User " + (i + 1));
                user.setLastName("User " + (i + 1));
                user.setEmail(defaultEmail);
                user.setPassword("123456");
                userRepository.save(user);
                System.out.println("User " + i + " created");
            }
        }
    }
}
