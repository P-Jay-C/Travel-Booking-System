package com.amalitech.org.userservice.security;
import com.amalitech.org.userservice.user.AppUser;
import com.amalitech.org.userservice.user.MyUserPrincipal;
import com.amalitech.org.userservice.user.converter.UserToUserDtoConverter;
import com.amalitech.org.userservice.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserToUserDtoConverter userToUserDtoConverter;

    public Map<String, Object> createLoginInfo(Authentication authentication) {
        Map<String, Object> loginResultMap = new HashMap<>();

        // Check if the principal is an instance of MyUserPrincipal
        if (authentication.getPrincipal() instanceof MyUserPrincipal principal) {
            AppUser appUser = principal.appUser();
            UserDto userDto = userToUserDtoConverter.convert(appUser);

            loginResultMap.put("userInfo", userDto);
        }

        String token = jwtProvider.createToken(authentication);
        loginResultMap.put("token", token);

        return loginResultMap;
    }


}
