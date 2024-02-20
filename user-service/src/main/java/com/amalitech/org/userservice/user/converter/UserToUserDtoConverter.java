package com.amalitech.org.userservice.user.converter;
import com.amalitech.org.userservice.user.AppUser;
import com.amalitech.org.userservice.user.dto.UserDto;
import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDtoConverter implements Converter<AppUser, UserDto> {
    @Override
    public UserDto convert(AppUser source) {
        return new UserDto(ObjectId.get(), source.getUsername(), source.getEmail(), source.getPassword(), source.isEnabled(), source.getRoles());
    }
}
