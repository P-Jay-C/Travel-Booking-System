package com.ecommerce.library.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto {
    @Size(min = 3, max = 10, message = "First name contains 3-10 characters")
    private String firstName;
    @Size(min = 3, max = 10, message = "Last name contains 3-10 characters")
    private String lastName;
    @NotEmpty(message = "Email is required")
    private String username;
    @Size(min = 5, max = 10, message = "Password contains 5-10 characters")
    @NotEmpty(message = "Password is required")
    private String password;
    private String repeatPassword;
}
