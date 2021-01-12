package com.server.traffic.logic.service.impl;

import com.server.traffic.constant.AppConstant;
import com.server.traffic.logic.conveter.CodeSignUpConverter;
import com.server.traffic.logic.conveter.UserConverter;
import com.server.traffic.logic.dto.CodeSignUpDTO;
import com.server.traffic.logic.dto.UserSignUpDTO;
import com.server.traffic.logic.dto.response.MessageSuccess;
import com.server.traffic.logic.entity.CodeSignUpEntity;
import com.server.traffic.logic.entity.UserEntity;
import com.server.traffic.logic.repository.CodeSignUpRepository;
import com.server.traffic.logic.repository.RoleRepository;
import com.server.traffic.logic.repository.UserRepository;
import com.server.traffic.logic.service.UserService;
import com.server.traffic.utils.CommonUtils;
import com.server.traffic.utils.MailUtils;
import com.server.traffic.utils.RandomUtils;
import com.server.traffic.utils.error.CustomException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * UserServiceImpl : class implements UserService {com.server.tradedoc.logic.service}
 *
 * @author DatDV
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private CodeSignUpRepository codeSignUpRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CodeSignUpConverter codeSignUpConverter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MailUtils mailUtils;

    @Autowired
    private UserConverter userConverter;


    /**
     * signUpUser : function signup user
     *
     * @param userSignUpDTO: param for signup {com.server.tradedoc.logic.dto}
     * @return MessageSuccess : response of API {com.server.tradedoc.logic.dto.reponse}
     * @throws MessagingException
     */
    @Override
    @Transactional
    public MessageSuccess signUpUser(UserSignUpDTO userSignUpDTO) throws MessagingException {
        MessageSuccess result = new MessageSuccess();
        // validate params
        if (StringUtils.isBlank(userSignUpDTO.getEmail())) {
            throw new CustomException("email not valid", CommonUtils.putError("code", "ERR_0034"));
        } else {
            if (!mailUtils.validate(userSignUpDTO.getEmail())) {
                throw new CustomException("code not valid", CommonUtils.putError("code", "ERR_0034"));
            }
        }
        if (userSignUpDTO.getPassword().length() < 8) {
            throw new CustomException("password is at least eight characters", CommonUtils.putError("password", "ERR_0034"));
        }
        // check user
        UserEntity user = userRepository.findOneByUsernameAndStatus(userSignUpDTO.getEmail(), AppConstant.ACTIVE.ACTIVE_STATUS);
        if (user != null) {
            throw new CustomException("username or email already exist", CommonUtils.putError("password", "ERR_0034"));
        }
        // check code empty or no empty
        if (StringUtils.isBlank(userSignUpDTO.getCode())) {
            // send mail confirm sign up to email of user sign up
            String uuid = RandomUtils.randomCode();
            CodeSignUpDTO codeSignUpDTO = new CodeSignUpDTO();
            codeSignUpDTO.setCode(uuid);
            codeSignUpDTO.setEmail(userSignUpDTO.getEmail());
            codeSignUpRepository.save(codeSignUpConverter.toEntity(codeSignUpDTO));
            String template = "support traffic!! \nthank you for sign up! \ncode for your using confirm signup account: " + uuid + "";
            String subject = "code confirm sign up";
            if (mailUtils.sendMailUseTemplate(template, null, userSignUpDTO.getEmail(), subject)) {
                result.setCodeSuccess("200");
                result.setMessageSuccess("Check your mail");
            } else {
                result.setCodeSuccess("500");
                result.setMessageSuccess("ERROR send mail");
            }
            return result;
        }
        CodeSignUpEntity codeSignUpEntity = codeSignUpRepository.findByCodeAndEmail(userSignUpDTO.getCode(), userSignUpDTO.getEmail());
        if (codeSignUpEntity.getCode().equals(userSignUpDTO.getCode())) {
            // sign up after confirm code sign up
            UserEntity userEntity = new UserEntity();
            userEntity.setStatus(1);
            userEntity.setUserName(userSignUpDTO.getEmail());
            userEntity.setEmail(userSignUpDTO.getEmail());
            userEntity.setPassWord(passwordEncoder.encode(userSignUpDTO.getPassword()));
            List<Long> roleId = new ArrayList<>();
            // auto sign up for user customer
            if (true) {
                roleId = Arrays.asList(3L);
            }
            userEntity.setRoles(roleRepository.findByIdIn(roleId));
            userEntity.setFullName(userSignUpDTO.getFullName());
            userEntity.setNumberPhone(userSignUpDTO.getPhoneNumber());
            userEntity.setCreatedDate(Instant.now());
            userEntity.setModifiedDate(Instant.now());
            userRepository.save(userEntity);
            codeSignUpRepository.deleteByCodeAndEmail(userSignUpDTO.getCode(), userSignUpDTO.getEmail());
            result.setCodeSuccess("200");
            result.setMessageSuccess("Sign Up Success");
        } else {
            throw new CustomException("invalid confirm code change password", CommonUtils.putError("code", "ERR_0034"));
        }

        // response
        return result;
    }
}
