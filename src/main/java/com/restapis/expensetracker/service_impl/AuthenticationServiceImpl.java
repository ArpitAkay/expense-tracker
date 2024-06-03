package com.restapis.expensetracker.service_impl;

import com.restapis.expensetracker.entity.UserInfo;
import com.restapis.expensetracker.exception.RestException;
import com.restapis.expensetracker.model.user_info.UserInfoRequest;
import com.restapis.expensetracker.repository.UserInfoRepository;
import com.restapis.expensetracker.service.AuthenticationService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserInfoRepository userInfoRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(UserInfoRepository userInfoRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userInfoRepository = userInfoRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserInfoRequest signup(UserInfoRequest userInfoRequest) throws RestException {
        String email = userInfoRequest.getEmail();

        Optional<UserInfo> isUserInfoExists = userInfoRepository.findByEmail(email);

        if(isUserInfoExists.isPresent()) {
            throw new RestException("User already exists");
        }

        UserInfo userInfo = modelMapper.map(userInfoRequest, UserInfo.class);
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        UserInfo userInfoInserted = userInfoRepository.save(userInfo);

        return modelMapper.map(userInfoInserted, UserInfoRequest.class);
    }
}
