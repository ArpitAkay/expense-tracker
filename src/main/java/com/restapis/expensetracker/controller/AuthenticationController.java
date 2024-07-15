package com.restapis.expensetracker.controller;

import com.restapis.expensetracker.constant.Endpoint;
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
import com.restapis.expensetracker.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(Endpoint.BASE_URL)
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

//    @Tag(name = "Signup", description = "This endpoint is used to sign up a new user.")
    @PostMapping(Endpoint.SIGN_UP)
    public ResponseEntity<ApiResponse> signup(@Valid @RequestBody UserInfoRequest userInfoRequest)
            throws RestException {
        return new ResponseEntity(authenticationService.signup(userInfoRequest), HttpStatus.CREATED);
    }

    @PostMapping(Endpoint.VERIFY_EMAIL)
    public ResponseEntity<ApiResponse> verifyEmail(@Valid @RequestBody VerifyEmailRequest verifyEmailRequest)
            throws RestException {
        return new ResponseEntity<>(authenticationService.verifyEmail(verifyEmailRequest), HttpStatus.OK);
    }

    @PostMapping(Endpoint.SEND_VERIFICATION_MAIL_AGAIN)
    public ResponseEntity<ApiResponse> sendVerificationMailAgain(
            @Valid @RequestBody SendVerificationMailAgainRequest sendVerificationMailAgainRequest) throws RestException {
        return new ResponseEntity<>(authenticationService.sendVerificationMailAgain(sendVerificationMailAgainRequest), HttpStatus.OK);
    }

    @PostMapping(Endpoint.LOGIN)
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest)
            throws RestException {
        return new ResponseEntity<>(authenticationService.login(loginRequest), HttpStatus.OK);
    }

    @PostMapping(Endpoint.FORGET_PASSWORD)
    public ResponseEntity<ApiResponse> forgetPassword(@Valid @RequestBody ForgetPasswordRequest forgetPasswordRequest)
            throws RestException {
        return new ResponseEntity<>(authenticationService.forgetPassword(forgetPasswordRequest), HttpStatus.OK);
    }

    @GetMapping(Endpoint.VALIDATE_RESET_PASSWORD_TOKEN)
    public ResponseEntity<ApiResponse> validateResetPasswordToken(
            @RequestParam(name = "token") UUID token) throws RestException {
        return new ResponseEntity<>(authenticationService.validateResetPasswordToken(token), HttpStatus.OK);
    }

    @PostMapping(Endpoint.RESET_PASSWORD)
    public ResponseEntity<ApiResponse> resetPassword(
            @RequestParam(name = "token") UUID token,
            @Valid @RequestBody ResetPasswordRequest resetPasswordRequest)
            throws RestException {
        return new ResponseEntity<>(authenticationService.resetPassword(token, resetPasswordRequest), HttpStatus.OK);
    }

    @GetMapping(Endpoint.RENEW_ACCESS_TOKEN)
    public ResponseEntity<RenewAccessTokenResponse> renewAccessToken(HttpServletRequest request) throws RestException {
        return new ResponseEntity(authenticationService.renewAccessToken(request), HttpStatus.OK);
    }
}
