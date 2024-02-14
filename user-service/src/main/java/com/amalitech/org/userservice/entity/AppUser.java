package com.amalitech.org.userservice.entity;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "appUsers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUser implements Serializable {
    @Id
    private String id;

    @NotEmpty(message = "username is required")
    @Indexed(unique = true)
    private String username;

    @NotEmpty(message = "Email is required")
    @Indexed(unique = true)
    private String email;

    @NotEmpty(message = "Password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters with a combination of upper, lower, special characters, and numbers")
    private String password;

    private boolean enabled;

    @NotEmpty(message = "roles are required")
    private String roles;
}

