package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.ChangePasswordRequest;
import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.City;
import com.ecommerce.library.model.Country;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.OldPassword;
import com.ecommerce.library.service.CityService;
import com.ecommerce.library.service.CountryService;
import com.ecommerce.library.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CountryService countryService;
    private final PasswordEncoder passwordEncoder;
    private final CityService cityService;

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.getCustomer(username);
        List<Country> countryList = countryService.findAll();
        List<City> cities = cityService.findAll();
        model.addAttribute("customer", customer);
        model.addAttribute("cities", cities);
        model.addAttribute("countries", countryList);
        model.addAttribute("title", "Profile");
        model.addAttribute("page", "Profile");
        return "customer-information";

    }

    @PostMapping("/update-profile")
    public String updateProfile(@Valid @ModelAttribute("customer") CustomerDto customerDto,
                                BindingResult result,
                                RedirectAttributes attributes,
                                Model model,
                                Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            List<Country> countryList = countryService.findAll();
            List<City> cities = cityService.findAll();
            model.addAttribute("countries", countryList);
            model.addAttribute("cities", cities);
            return "customer-information";
        }

        customerService.update(customerDto);

        // Retrieve the updated customer data from the database
        Customer customerUpdate = customerService.getCustomer(principal.getName());

        // Set flash attribute and redirect
        attributes.addFlashAttribute("success", "Update successfully!");
        model.addAttribute("customer", customerUpdate);
        return "redirect:/profile";
    }


    @GetMapping("/change-password")
    public String changePassword(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Change password");
        model.addAttribute("page", "Change password");
        model.addAttribute("passwordChangeRequest", new ChangePasswordRequest());
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePass(@ModelAttribute ChangePasswordRequest changePasswordRequest,
                             RedirectAttributes attributes,
                             Principal principal,Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        Customer customer = customerService.getCustomer(principal.getName());

        if (!isPasswordChangeValid(customer, changePasswordRequest, attributes)) {
            return "redirect:/profile";
        }

        OldPassword oldPassword = OldPassword.builder()
                .customer(customer)
                .password(passwordEncoder.encode(changePasswordRequest.getNewPassword()))
                .createdAt(LocalDateTime.now())
                .build();

        customer.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        customerService.changePass(customer, oldPassword);

        attributes.addFlashAttribute("success", "Your password has been changed successfully!");
        model.addAttribute("passwordChangeRequest", changePasswordRequest);
        return "redirect:/profile";
    }

    private boolean isPasswordChangeValid(Customer customer, ChangePasswordRequest changePasswordRequest, RedirectAttributes attributes) {
        if (isPasswordReused(customer, changePasswordRequest.getNewPassword())) {
            attributes.addFlashAttribute("error", "You cannot reuse old passwords.");
            return false;
        }

        if (passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), customer.getPassword())
                && !passwordEncoder.matches(changePasswordRequest.getNewPassword(), changePasswordRequest.getCurrentPassword())
                && changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmationPassword())
                && changePasswordRequest.getNewPassword().length() >= 5) {
            return true;
        } else {
            attributes.addFlashAttribute("error", "Invalid password change request.");
            return false;
        }
    }

    private boolean isPasswordReused(Customer customer, String newPassword) {
        return customer.getOldPasswords().stream()
                .anyMatch(oldPassword -> passwordEncoder.matches(newPassword, oldPassword.getPassword()));
    }

}
