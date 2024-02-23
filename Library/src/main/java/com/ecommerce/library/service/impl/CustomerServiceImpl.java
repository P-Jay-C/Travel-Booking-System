package com.ecommerce.library.service.impl;

import com.ecommerce.library.Exception.BaseException;
import com.ecommerce.library.Exception.EmailNotActiveException;
import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.EmailDetails;
import com.ecommerce.library.model.Provider;
import com.ecommerce.library.repository.CustomerRepository;
import com.ecommerce.library.repository.RoleRepository;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
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
        // Send email alert
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(customer.getUsername())
                .subject("ACCOUNT CREATION")
                .messageBody("Dear " + customer.getFirstName() + " " + customer.getLastName() + ",\n\n"
                        + "Congratulations! Your account has been successfully created.\n\n"
                        + "Thank you for choosing our services.\n\n"
                        + "Best regards,\n The Best Travel Centre")
                .build();

        boolean emailSent = emailService.sendEmailAlert(emailDetails);

        if (!emailSent) {
            throw new EmailNotActiveException("Email not active");
        }

        return customerRepository.save(customer);
    }

    @Override
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    public CustomerDto getCustomer(String username) {
        CustomerDto customerDto = new CustomerDto();
        Customer customer = customerRepository.findByUsername(username);
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setUsername(customer.getUsername());
        customerDto.setPassword(customer.getPassword());
        customerDto.setAddress(customer.getAddress());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        customerDto.setCity(customer.getCity());
        customerDto.setCountry(customer.getCountry());
        return customerDto;
    }

    @Override
    public Customer changePass(CustomerDto customerDto) {
        Customer customer = customerRepository.findByUsername(customerDto.getUsername());
        customer.setPassword(customerDto.getPassword());
        return customerRepository.save(customer);
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
}
