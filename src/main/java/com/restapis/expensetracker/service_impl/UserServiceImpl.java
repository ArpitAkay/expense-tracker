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

    public UserServiceImpl(
            UserInfoRepository userInfoRepository,
            ModelMapper modelMapper) {
        this.userInfoRepository = userInfoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserInfoResponse getUserInfo(int userId) throws RestException {
        UserInfo userInfo = userInfoRepository.findById(userId).orElseThrow(() -> new RestException(
                "User not found"
        ));

        String email = SecurityUtil.retrieveEmailFromSecurityContext();

        if(!userInfo.getEmail().equals(email)) {
            throw new RestException("You cannot see other user's information");
        }

        return modelMapper.map(userInfo, UserInfoResponse.class);
    }
}
