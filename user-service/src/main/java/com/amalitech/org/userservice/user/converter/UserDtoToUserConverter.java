package com.amalitech.org.userservice.user.converter;
import com.amalitech.org.userservice.user.AppUser;
import com.amalitech.org.userservice.user.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoToUserConverter implements Converter<UserDto, AppUser> {
    @Override
    public AppUser convert(UserDto source) {

        AppUser user = new AppUser();

        user.setUsername(source.username());
        user.setEnabled(source.enabled());
        user.setRoles(source.roles());

        return  user;
    }
}
