package com.ecommerce.library.service;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.model.UserSession;
import com.ecommerce.library.repository.UserSessionRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class UserSessionService {
    private final UserSessionRepository userSessionRepository;

    public UserSessionService(UserSessionRepository userSessionRepository) {
        this.userSessionRepository = userSessionRepository;
    }

    public UserSession createGuestUserSession(HttpSession httpSession) {
        UserSession userSession = new UserSession();
        userSession.setSessionId(httpSession.getId());
        userSession.setShoppingCart(new ShoppingCart());
        return userSessionRepository.save(userSession);
    }

    public UserSession getUserSession(String sessionId) {
        return userSessionRepository.findBySessionId(sessionId);
    }

}
