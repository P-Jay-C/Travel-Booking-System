package com.ecommerce.library.event.Listener;
import com.ecommerce.library.Exception.EmailNotActiveException;
import com.ecommerce.library.event.RegistrationCompleteEvent;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.EmailDetails;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private CustomerService userService;
    private final EmailService emailService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
      
        // Create the verification Token for the User with link

        Customer user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token, user);

        // Send mail to user
        String url = event.getApplicationUrl()+"/verifyRegistration?token=" + token;

                // Send email alert
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(user.getUsername())
                .subject("ACCOUNT CREATION")
                .messageBody("Dear " + user.getFirstName() + " " + user.getLastName() + ",\n\n"
                        + "Congratulations! Your account has been successfully created.\n\n"
                        + "Please click the following link to verify your email address:\n"
                        + url + "\n\n"
                        + "Thank you for choosing our services.\n\n"
                        + "Best regards,\n The Best Travel Centre")
                .build();

        try {
            boolean emailSent = emailService.sendEmailAlert(emailDetails);
            if (!emailSent) {
                throw new EmailNotActiveException("Email not sent");
            }
        } catch (Exception e) {
            log.error("Error sending verification email to user: {}", user.getUsername(), e);
            throw new EmailNotActiveException("Error sending verification email");
        }

        log.info("Click the link to verify your account: {}", url);
    }    
}
