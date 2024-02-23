package com.ecommerce.library.service.oauth2;

import com.ecommerce.library.Exception.BaseException;
import com.ecommerce.library.model.Provider;

import java.util.Map;

public class OAuth2UserDetailFactory {

    public static OAuth2UserDetails getOAuthUserDetail(String registrationId, Map<String, Object> attributes){
        if(registrationId.equals(Provider.google.name())){
            return new OAuth2GoogleUser(attributes);
        }else if(registrationId.equals(Provider.facebook.name())){
            return new OAuth2FacebookUser(attributes);
        }else if(registrationId.equals(Provider.github.name())){
            return new OAuth2GitHubUser(attributes);
        }else {
            throw new BaseException("400", "Sorry! Login with " + registrationId + " is not supported");
        }
    }
}
