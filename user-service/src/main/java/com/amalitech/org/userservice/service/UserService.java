package com.amalitech.org.userservice.service;
import com.amalitech.org.userservice.entity.AppUser;
import com.amalitech.org.userservice.user.dto.ChangePasswordRequest;
import org.bson.types.ObjectId;

import java.util.List;

public interface UserService {
    AppUser findById(ObjectId id);

    List<AppUser> findAll();

    AppUser save(AppUser user);

    AppUser update(ObjectId id, AppUser update);

    void delete(ObjectId id);

    void changePassword(ObjectId userId, ChangePasswordRequest changePasswordRequest);

    String generateToken(String username);

    void validateToken(String token);
}