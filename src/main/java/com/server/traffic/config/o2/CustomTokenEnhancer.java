package com.server.traffic.config.o2;

import com.server.traffic.logic.dto.o2auth.UserPrincipalOauth2;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomTokenEnhancer extends JwtAccessTokenConverter {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        UserPrincipalOauth2 user = (UserPrincipalOauth2) authentication.getPrincipal();
        Map<String , Object> info = new LinkedHashMap<String, Object>(accessToken.getAdditionalInformation());

        info.put("roles" , user.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList()));
        info.put("fullName" , user.getFullName());

        DefaultOAuth2AccessToken customerAccessToken = new DefaultOAuth2AccessToken(accessToken);
        customerAccessToken.setAdditionalInformation(info);
        return super.enhance(customerAccessToken , authentication);
    }

}
