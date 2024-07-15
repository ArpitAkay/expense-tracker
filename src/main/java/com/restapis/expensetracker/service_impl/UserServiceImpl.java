package com.restapis.expensetracker.service_impl;

import com.restapis.expensetracker.entity.UserInfo;
import com.restapis.expensetracker.exception.RestException;
import com.restapis.expensetracker.model.sign_up.UserInfoResponse;
import com.restapis.expensetracker.repository.UserInfoRepository;
import com.restapis.expensetracker.service.UserService;
import com.restapis.expensetracker.util.SecurityUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserInfoRepository userInfoRepository;
    private final ModelMapper modelMapper;
    private final SecurityUtil securityUtil;

    public UserServiceImpl(
            UserInfoRepository userInfoRepository,
            ModelMapper modelMapper, SecurityUtil securityUtil
    ) {
        this.userInfoRepository = userInfoRepository;
        this.modelMapper = modelMapper;
        this.securityUtil = securityUtil;
    }

    @Override
    public UserInfoResponse getUserInfo(int userId) throws RestException {
        UserInfo userInfo = securityUtil.checkIfUserIsAuthorized(userId);
        return modelMapper.map(userInfo, UserInfoResponse.class);
    }
}
