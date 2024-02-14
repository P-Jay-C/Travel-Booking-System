package com.amalitech.org.userservice.Controller;


import com.amalitech.org.userservice.service.AuthService;
import com.amalitech.org.userservice.entity.AppUser;
import com.amalitech.org.userservice.user.dto.AuthRequest;
import com.amalitech.org.userservice.user.dto.ChangePasswordRequest;
import com.amalitech.org.userservice.util.Result;
import com.amalitech.org.userservice.util.StatusCode;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public Result addUser(@Valid @RequestBody AppUser user) {
        AppUser savedUser = service.save(user);

        return new Result(true, StatusCode.SUCCESS, "Add Success", savedUser);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            return service.generateToken(authRequest.getUsername());
        } else {
            throw new RuntimeException("invalid access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token is valid";
    }

    @PostMapping("/change-password/{userId}")
    public ResponseEntity<String> changePassword(
            @PathVariable String userId,
            @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {

        ObjectId objectId = new ObjectId(userId);

        try {
            service.changePassword(objectId, changePasswordRequest);
            return ResponseEntity.ok("Password changed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/users/{userId}")
    public Result findUserById(@PathVariable String userId) {
        AppUser user = service.findById(new ObjectId(userId));

        return new Result(true, StatusCode.SUCCESS, "Find Success", user);
    }

    @GetMapping("/users")
    public Result findAllUsers() {
        List<AppUser> users = service.findAll();

        return new Result(true, StatusCode.SUCCESS, "Find All Success", users);
    }

    @DeleteMapping("/users/{userId}")
    public Result deleteUser(@PathVariable String userId) {
        service.delete(new ObjectId(userId));
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }

}

