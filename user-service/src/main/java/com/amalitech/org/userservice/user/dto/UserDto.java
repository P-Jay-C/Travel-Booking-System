package com.amalitech.org.userservice.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.bson.types.ObjectId;

public record UserDto(
        @Id
        @JsonIgnore
        ObjectId id,

        @NotEmpty(message = "username is required")
        String username,

        @NotEmpty(message = "Email is required")
        String email,

        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "Password must be at least 8 characters with a combination of upper, lower, special characters, and numbers")
        String password,

        boolean enabled,

        @NotEmpty(message = "roles are required")
        String roles) {
}

