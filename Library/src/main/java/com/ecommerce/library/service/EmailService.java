package com.ecommerce.library.service;


import com.ecommerce.library.model.EmailDetails;

public interface EmailService {
    boolean sendEmailAlert(EmailDetails emailDetails);
}
