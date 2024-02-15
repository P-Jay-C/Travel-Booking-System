package com.amalitech.org.userservice.service;

import com.amalitech.org.userservice.Repository.UserRepository;
import com.amalitech.org.userservice.entity.AppUser;
import com.amalitech.org.userservice.exception.ObjectNotFoundException;
import com.amalitech.org.userservice.user.dto.ChangePasswordRequest;
import com.amalitech.org.userservice.user.dto.EmailDetails;
import jakarta.transaction.Transactional;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Override
    public AppUser findById(ObjectId id) {
        return userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("user", String.valueOf(id)));
    }

    @Override
    public List<AppUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public AppUser save(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);

        // Send email alert
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("ACCOUNT CREATION")
                .messageBody("Dear " + user.getUsername() + ",\n\n"
                        + "Congratulations! Your account has been successfully created.\n\n"
                        + "Thank you for choosing our services.\n\n"
                        + "Best regards,\n The Best Travel Centre")  // Corrected line
                .build();

        emailService.sendEmailAlert(emailDetails);
        return user;
    }

    @Override
    public AppUser update(ObjectId id, AppUser update) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(update.getUsername());
                    user.setEmail(update.getEmail());
                    user.setEnabled(update.isEnabled());
                    user.setRoles(update.getRoles());

                    return this.userRepository.save(user);
                })
                .orElseThrow(() -> new ObjectNotFoundException("user", String.valueOf(id)));
    }

    @Override
    public void delete(ObjectId id) {
        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("user", String.valueOf(id)));
        userRepository.deleteById(id);
    }

    @Override
    public void changePassword(ObjectId userId, ChangePasswordRequest changePasswordRequest) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("user", String.valueOf(userId)));

        // Validate current password
        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid current password");
        }

        // Ensure new password is different from the current password
        if (passwordEncoder.matches(changePasswordRequest.getNewPassword(), user.getPassword())) {
            throw new RuntimeException("New password must be different from the current password");
        }

        // Ensure confirmation password matches the new password
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmationPassword())) {
            throw new RuntimeException("Confirmation password does not match the new password");
        }

        // Update the user's password
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));

        // Save the updated user
        userRepository.save(user);

        // Send email alert
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("PASSWORD CHANGE")
                .messageBody("Dear " + user.getUsername() + ",\n\n"
                        + "Your password has been successfully changed.\n\n"
                        + "If you did not make this change, please contact us immediately.\n\n"
                        + "Best regards,\n The Best Travel Centre")
                .build();

        emailService.sendEmailAlert(emailDetails);
    }

    @Override
    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    @Override
    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}

