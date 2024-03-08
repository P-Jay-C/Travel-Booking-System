package com.ecommerce.library.service.impl;

import com.ecommerce.library.Exception.BaseException;
import com.ecommerce.library.Exception.EmailNotActiveException;
import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.*;
import com.ecommerce.library.repository.CustomerRepository;
import com.ecommerce.library.repository.RoleRepository;
import com.ecommerce.library.repository.VerificationTokenRepository;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Override
    public Customer save(CustomerDto customerDto) {
        // Check if the username already exists
        if (customerRepository.existsByUsername(customerDto.getUsername())) {
            throw new BaseException("500","Username already exists");
        }

        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setPassword(customerDto.getPassword());
        customer.setUsername(customerDto.getUsername());
        customer.setAddress(customerDto.getAddress());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setCountry(customerDto.getCountry());
        customer.setRoles(Arrays.asList(roleRepository.findByName("CUSTOMER")));

        customer.setProviderId(Provider.local.name());

        return customerRepository.save(customer);
    }

    @Override
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    public Customer getCustomer(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    public Customer update(CustomerDto dto) {
        Customer customer = customerRepository.findByUsername(dto.getUsername());
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setPassword(dto.getPassword());
        customer.setUsername(dto.getUsername());
        customer.setAddress(dto.getAddress());
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setCountry(dto.getCountry());
        return customerRepository.save(customer);
    }

    @Override
    public void changePass(Customer customerDto, OldPassword oldPassword) {
        Customer customer = customerRepository.findByUsername(customerDto.getUsername());

        // Store the old password
        if (oldPassword != null) {
            customer.getOldPasswords().add(oldPassword);
        }

        // Update the current password by encoding the new password
        customer.setPassword(customerDto.getPassword());

        customerRepository.save(customer);
    }

    @Override
    public void saveVerificationTokenForUser(String token, Customer user) {

        VerificationToken verificationToken = new VerificationToken(user, token);

        verificationTokenRepository.save(verificationToken);

    }

    @Override
    public String validateVerificationToken(String token) {

        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

        if (verificationToken == null) {
            return "invalid";
        }

        Customer user = verificationToken.getCustomer();
        Calendar calendar = Calendar.getInstance();

        if ((verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            verificationTokenRepository.delete(verificationToken);
            return "expired";
        }

        user.setEnabled(true);
        customerRepository.save(user);

        return "valid";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken);

        if (verificationToken != null) {
            verificationToken.setToken(UUID.randomUUID().toString());
            verificationTokenRepository.save(verificationToken);
        }

        return verificationToken;
    }

    @Override
    public Customer findUserByEmail(String email) {

        return customerRepository.findByUsername(email);
    }

}
