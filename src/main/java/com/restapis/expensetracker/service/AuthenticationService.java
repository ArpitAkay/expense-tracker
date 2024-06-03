package com.restapis.expensetracker.service;

import com.restapis.expensetracker.entity.UserInfo;
import com.restapis.expensetracker.exception.RestException;
import com.restapis.expensetracker.model.user_info.UserInfoRequest;

public interface AuthenticationService {
    UserInfoRequest signup(UserInfoRequest userInfoRequest) throws RestException;
}
