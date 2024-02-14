package com.amalitech.org.userservice.service;


import com.amalitech.org.userservice.user.dto.EmailDetails;

public interface EmailService {
    void sendEmailAlert(EmailDetails emailDetails);
}
