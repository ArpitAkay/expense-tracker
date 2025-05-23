package com.restapis.expensetracker.service;

import com.restapis.expensetracker.exception.RestException;
import com.restapis.expensetracker.model.api.ApiResponse;
import com.restapis.expensetracker.model.forget_password.ForgetPasswordRequest;
import com.restapis.expensetracker.model.login.LoginRequest;
import com.restapis.expensetracker.model.login.LoginResponse;
import com.restapis.expensetracker.model.renew_access_token.RenewAccessTokenResponse;
import com.restapis.expensetracker.model.reset_password.ResetPasswordRequest;
import com.restapis.expensetracker.model.send_verification_mail_again.SendVerificationMailAgainRequest;
import com.restapis.expensetracker.model.sign_up.UserInfoRequest;
import com.restapis.expensetracker.model.verify_email.VerifyEmailRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.util.UUID;

public interface AuthenticationService {
    ApiResponse signup(UserInfoRequest userInfoRequest) throws RestException;

    ApiResponse verifyEmail(VerifyEmailRequest verifyEmailRequest) throws RestException;

    ApiResponse sendVerificationMailAgain(
            SendVerificationMailAgainRequest sendVerificationMailAgainRequest
    ) throws RestException;

    LoginResponse login(LoginRequest loginRequest) throws RestException;

    ApiResponse forgetPassword(ForgetPasswordRequest forgetPasswordRequest) throws RestException;

    ApiResponse validateResetPasswordToken(UUID token) throws RestException;

    ApiResponse resetPassword(UUID token, ResetPasswordRequest resetPasswordRequest) throws RestException;

    RenewAccessTokenResponse renewAccessToken(HttpServletRequest request) throws RestException;
}
