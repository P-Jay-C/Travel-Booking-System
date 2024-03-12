package com.ecommerce.library.repository;

import com.ecommerce.library.model.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    UserSession findBySessionId(String sessionId);
}
