package com.restapis.expensetracker.service;

import com.restapis.expensetracker.exception.RestException;
import com.restapis.expensetracker.model.sign_up.UserInfoResponse;

public interface UserService {
    UserInfoResponse getUserInfo(int userId) throws RestException;
}
