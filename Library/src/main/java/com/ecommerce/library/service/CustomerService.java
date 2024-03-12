package com.ecommerce.library.service;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.OldPassword;
import com.ecommerce.library.model.VerificationToken;

public interface CustomerService {
    Customer save(CustomerDto customerDto);

    Customer save(Customer customerDto);

    Customer findByUsername(String username);

    Customer update(CustomerDto customerDto);

    Customer getCustomer(String username);

    void changePass(Customer customer, OldPassword oldPassword);


    void saveVerificationTokenForUser(String token, Customer customer);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);

    Customer findUserByEmail(String email);
}
