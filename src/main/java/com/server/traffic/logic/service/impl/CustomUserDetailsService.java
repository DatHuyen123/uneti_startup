package com.server.traffic.logic.service.impl;

import com.server.traffic.constant.AppConstant;
import com.server.traffic.logic.dto.o2auth.UserPrincipalOauth2;
import com.server.traffic.logic.entity.UserEntity;
import com.server.traffic.logic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * CustomUserDetailsService
 *
 * @author DatDV
 */
@Service(value = "userCustomService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findOneByUsernameAndStatus(username, AppConstant.ACTIVE.ACTIVE_STATUS);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return UserPrincipalOauth2.createPrincipalOauth2(userEntity);
    }
}
