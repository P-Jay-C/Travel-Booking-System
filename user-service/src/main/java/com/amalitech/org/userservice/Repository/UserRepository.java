package com.amalitech.org.userservice.Repository;

import com.amalitech.org.userservice.entity.AppUser;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<AppUser, ObjectId> {
    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findByEmail(String email);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}


