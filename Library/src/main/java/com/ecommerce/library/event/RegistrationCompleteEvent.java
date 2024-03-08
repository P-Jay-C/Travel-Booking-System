package com.ecommerce.library.event;
import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.Customer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent{

    private Customer user;
    private String applicationUrl;

    public RegistrationCompleteEvent(Customer user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
    
}
