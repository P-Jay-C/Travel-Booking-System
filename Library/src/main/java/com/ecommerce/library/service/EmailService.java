package com.ecommerce.library.service;


import com.ecommerce.library.model.EmailDetails;

public interface EmailService {
    void sendEmailAlert(EmailDetails emailDetails);
}
