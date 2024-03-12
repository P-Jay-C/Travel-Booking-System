package com.ecommerce.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@Entity
@Table(name = "customers", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;
    private String providerId;
    private String firstName;
    private String lastName;
    @NotEmpty(message = "Email is required")
    private String username;
    private String password;
    private String phoneNumber;
    private String address;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "name", referencedColumnName = "id")
    private City city;
    private String country;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(name = "customer_role", joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Collection<Role> roles;

//    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart cart;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OldPassword> oldPasswords;

    private boolean enabled = false;

    public Customer() {
        this.cart = new ShoppingCart();
        this.orders = new ArrayList<>();
        this.oldPasswords = new HashSet<>();
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + (firstName != null ? firstName : "null") + '\'' +
                ", lastName='" + (lastName != null ? lastName : "null") + '\'' +
                ", username='" + (username != null ? username : "null") + '\'' +
                ", password='" + (password != null ? password : "null") + '\'' +
                ", phoneNumber='" + (phoneNumber != null ? phoneNumber : "null") + '\'' +
                ", address='" + (address != null ? address : "null") + '\'' +
                ", city=" + (city != null ? city.getName() : "null") +
                ", country='" + (country != null ? country : "null") + '\'' +
                ", roles=" + (roles != null ? roles : "null") +
                ", cart=" + (cart != null ? cart.getId() : "null") +
                ", orders=" + (orders != null ? orders.size() : "null") +
                '}';
    }
}
