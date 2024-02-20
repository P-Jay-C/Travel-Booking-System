package com.amalitech.org.userservice.util;
import com.amalitech.org.userservice.user.AppUser;
import com.amalitech.org.userservice.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DBDataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public DBDataInitializer(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        AppUser u2 = new AppUser();
        u2.setId("5f5b3d38d4c5de5a015c18c3");
        u2.setUsername("eric");
        u2.setEmail("ericagyei@gmail.com");

        // Use the password encoder to encode the password
        u2.setPassword(passwordEncoder.encode("eric@123"));
        u2.setEnabled(true);
        u2.setRoles("admin user");

        userService.save(u2);
    }
}
