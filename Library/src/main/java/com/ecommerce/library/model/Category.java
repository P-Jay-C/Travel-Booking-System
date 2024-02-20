package com.ecommerce.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder

@Table(name = "categories", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;
    @NotBlank
    private String name;
    private String description;

    @NotBlank
    @Column(name = "code")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Code should only contain alphanumeric characters")
    private String code;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String icon;

//    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
//    private List<BookingOffer> bookingOffers;

    @Column(name = "is_activated")
    private boolean activated;

    @Column(name = "is_deleted")
    private boolean deleted;
}

