package com.server.traffic.logic.service;

import com.server.traffic.logic.dto.UserSignUpDTO;
import com.server.traffic.logic.dto.response.MessageSuccess;

import javax.mail.MessagingException;

public interface UserService {
    MessageSuccess signUpUser(UserSignUpDTO userSignUpDTO) throws MessagingException;
}
