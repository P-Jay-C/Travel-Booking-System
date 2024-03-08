package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.event.RegistrationCompleteEvent;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.VerificationToken;
import com.ecommerce.library.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {
    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private ApplicationEventPublisher publisher;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("title", "Login Page");
        model.addAttribute("page", "Home");
        return "login";
    }


    @GetMapping("/verification-success")
    public String verificationSuccessPage() {
        return "verification-success";
    }

    @GetMapping("/verification-failure")
    public String verificationFailurePage() {
        return "verification-failure";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("page", "Register");
        model.addAttribute("customerDto", new CustomerDto());
        return "register";
    }


    @PostMapping("/do-register")
    public String registerCustomer(@Valid @ModelAttribute("customerDto") CustomerDto customerDto,
                                   BindingResult result,
                                   Model model,
                                   final HttpServletRequest request) {
        try {
            // Check for validation errors
            if (result.hasErrors()) {
                model.addAttribute("customerDto", customerDto);
                return "register";
            }

            // Check if the username (email) is already registered
            String username = customerDto.getUsername();
            if (customerService.findByUsername(username) != null) {
                model.addAttribute("customerDto", customerDto);
                model.addAttribute("error", "Email has been registered!");
                return "register";
            }

            // Check if passwords match
            if (!customerDto.getPassword().equals(customerDto.getConfirmPassword())) {
                model.addAttribute("error", "Passwords do not match");
                model.addAttribute("customerDto", customerDto);
                return "register";
            }

            // Encode the password and save the customer
            customerDto.setPassword(passwordEncoder.encode(customerDto.getPassword()));
            Customer customer  = customerService.save(customerDto);

            // Add success message
            model.addAttribute("success", "Registration successful!");

            // Publish registration completion event
            publisher.publishEvent(
                    new RegistrationCompleteEvent(
                            customer,
                            applicationUrl(request)
                    ));

            // Return the view
            return "register";

        } catch (Exception e) {
            log.error("Error during registration", e);
            model.addAttribute("error", "Server error, please try again later!");
            return "register";
        }
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token){

        String result = customerService.validateVerificationToken(token);

        if(result.equalsIgnoreCase("valid")){
            return "redirect:/verification-success";
        }

        else {
            return "verification-failure";
        }
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort()+
                request.getContextPath();
    }
}
