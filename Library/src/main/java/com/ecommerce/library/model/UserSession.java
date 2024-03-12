package com.ecommerce.library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class UserSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sessionId;

    // An association to the shopping cart
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart shoppingCart;

    // Timestamp for session creation
    private LocalDateTime creationTime;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public UserSession() {
        // Default constructor
    }

    public UserSession(String sessionId) {
        this.sessionId = sessionId;
        this.shoppingCart = new ShoppingCart();
        this.creationTime = LocalDateTime.now();
    }
}
