package com.ecommerce.library.dto;

import com.ecommerce.library.model.City;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Size(min = 3, max = 15, message = "Password contains 3-10 characters")
    @NotEmpty(message = "Password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters with a combination of upper, lower, special characters, and numbers")

    private String password;

    @Size(min = 10, max = 15, message = "Phone number contains 10-15 characters")
    private String phoneNumber;

    private String address;
    @NotEmpty(message = "Password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters with a combination of upper, lower, special characters, and numbers")
    private String confirmPassword;
    private City city;
    private String image;
    private String country;
}
