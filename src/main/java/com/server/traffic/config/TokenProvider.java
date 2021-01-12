package com.server.traffic.config;

import com.server.traffic.constant.AppConstant;
import com.server.traffic.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Component
public class TokenProvider {
    @Autowired
    private TokenStore tokenStore;

    public String getUsernameFromToken(){
        String username = getTokenInfo(AppConstant.O2Constants.USER_NAME).toString();
        return username;
    }

    UsernamePasswordAuthenticationToken getAuthentication(Collection<? extends GrantedAuthority> authorities , final UserDetails userDetails){
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    public Object getTokenInfo(String key){
        Optional<String> tokenValue = SecurityUtils.getTokenValue();
        if (!tokenValue.isPresent()) {
            return null;
        }
        final OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue.get());
        Map<String, Object> additionalInformation = accessToken.getAdditionalInformation();
        return additionalInformation.get(key);
    }
}
