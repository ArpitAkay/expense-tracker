package com.restapis.expensetracker.service;

import com.restapis.expensetracker.exception.RestException;
import com.restapis.expensetracker.model.api.ApiResponse;
import com.restapis.expensetracker.model.user_pin.UserPinRequest;

public interface UserPinService {
    ApiResponse addedUserPin(int userId, UserPinRequest userPinRequest) throws RestException;

    ApiResponse verifyUserPin(int userId, UserPinRequest userPinRequest) throws RestException;
}
