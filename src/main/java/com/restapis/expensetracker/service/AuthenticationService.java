package com.restapis.expensetracker.service;

import com.restapis.expensetracker.exception.RestException;
import com.restapis.expensetracker.model.api.ApiResponse;
import com.restapis.expensetracker.model.forget_password.ForgetPasswordRequest;
import com.restapis.expensetracker.model.login.LoginRequest;
import com.restapis.expensetracker.model.login.LoginResponse;
import com.restapis.expensetracker.model.reset_password.ResetPasswordRequest;
import com.restapis.expensetracker.model.send_otp_mail_again.SendOtpMailAgainRequest;
import com.restapis.expensetracker.model.sign_up.UserInfoRequest;
import com.restapis.expensetracker.model.sign_up.UserInfoResponse;
import com.restapis.expensetracker.model.verify_otp.OtpRequest;

import java.util.UUID;

public interface AuthenticationService {
    UserInfoResponse signup(UserInfoRequest userInfoRequest) throws RestException;

    ApiResponse verifyOtp(OtpRequest otpRequest) throws RestException;

    ApiResponse sendOtpMailAgain(SendOtpMailAgainRequest sendOtpMailAgainRequest) throws RestException;

    LoginResponse login(LoginRequest loginRequest) throws RestException;

    ApiResponse forgetPassword(ForgetPasswordRequest forgetPasswordRequest) throws RestException;

    ApiResponse validateResetPasswordToken(UUID token) throws RestException;

    ApiResponse resetPassword(UUID token, ResetPasswordRequest resetPasswordRequest) throws RestException;
}
