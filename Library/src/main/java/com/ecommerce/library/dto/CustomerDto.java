package com.ecommerce.library.dto;

import com.ecommerce.library.model.City;
import com.ecommerce.library.model.OldPassword;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    @Size(min = 3, max = 10, message = "First name contains 3-10 characters")
    private String firstName;

    @Size(min = 3, max = 10, message = "Last name contains 3-10 characters")
    private String lastName;
    @NotEmpty(message = "Email is required")
    private String username;
    private String password;
    @Size(min = 10, max = 15, message = "Phone number contains 10-15 characters")
    private String phoneNumber;

    private String address;
    @NotEmpty(message = "Password is required")
    private String confirmPassword;
    private City city;
    private String image;
    private String country;
    private Set<OldPassword> oldPasswords;
}
