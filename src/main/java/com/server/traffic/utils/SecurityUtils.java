package com.server.traffic.utils;

import com.server.traffic.logic.dto.o2auth.UserPrincipalOauth2;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.Optional;

/**
 * SecurityUtils
 *
 * @author DatDV
 */
public class SecurityUtils {

    public static UserPrincipalOauth2 getPrincipal(){
        UserPrincipalOauth2 userPrincipalOauth2 = (UserPrincipalOauth2) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userPrincipalOauth2;
    }

    public static Optional<String> getTokenValue(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Optional<String> tokenValue = Optional.ofNullable(securityContext.getAuthentication()).map(authentication -> {
            if(authentication instanceof OAuth2Authentication){
                Object details = authentication.getDetails();
                if(details instanceof OAuth2AuthenticationDetails){
                    OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) details;
                    return oAuth2AuthenticationDetails.getTokenValue();
                }
            }
            return  null;
        });
        return tokenValue;
    }

}
