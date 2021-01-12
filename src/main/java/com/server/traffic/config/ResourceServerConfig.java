package com.server.traffic.config;

import com.server.traffic.constant.AppConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    public void configure(ResourceServerSecurityConfigurer resourceServerSecurityConfigurer){
        resourceServerSecurityConfigurer.resourceId(AppConstant.ResourceServer.RESOURCE_ID).stateless(false);
    }

    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.
                anonymous().disable()
                .authorizeRequests()
                .antMatchers("/api/client/**").permitAll()
                .antMatchers("/api/admin/**").access("hasRole('MANAGER')")
                .antMatchers("/api/staff/**").access("hasRole('STAFF')")
                .antMatchers("/api/customer/**").access("hasRole('CUSTOMER')")
                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
}
