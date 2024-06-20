package com.restapis.expensetracker.service_impl;

import com.restapis.expensetracker.entity.UserInfo;
import com.restapis.expensetracker.exception.RestException;
import com.restapis.expensetracker.model.api.ApiResponse;
import com.restapis.expensetracker.model.user_pin.UserPinRequest;
import com.restapis.expensetracker.repository.UserInfoRepository;
import com.restapis.expensetracker.service.UserPinService;
import com.restapis.expensetracker.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserPinServiceImpl implements UserPinService {
    private final PasswordEncoder passwordEncoder;
    private final UserInfoRepository userInfoRepository;
    private final SecurityUtil securityUtil;

    public UserPinServiceImpl(
            PasswordEncoder passwordEncoder,
            UserInfoRepository userInfoRepository, SecurityUtil securityUtil) {
        this.passwordEncoder = passwordEncoder;
        this.userInfoRepository = userInfoRepository;
        this.securityUtil = securityUtil;
    }

    @Override
    public ApiResponse addedUserPin(int userId, UserPinRequest userPinRequest) throws RestException {
        UserInfo userInfo = securityUtil.checkIfUserIsAuthorized(userId);

        String pin = userPinRequest.getPin();
        String encodedPin = passwordEncoder.encode(pin);

        userInfo.setPin(encodedPin);
        userInfoRepository.save(userInfo);

        return new ApiResponse("Pin added successfully");
    }

    @Override
    public ApiResponse verifyUserPin(int userId, UserPinRequest userPinRequest) throws RestException {
        UserInfo userInfo = securityUtil.checkIfUserIsAuthorized(userId);

        String pin = userPinRequest.getPin();

        if(passwordEncoder.matches(pin, userInfo.getPin())) {
            return new ApiResponse("Pin verified successfully");
        } else {
            throw new RestException("Invalid pin");
        }
    }
}
