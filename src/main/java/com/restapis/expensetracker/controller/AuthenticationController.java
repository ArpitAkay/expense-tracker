package com.restapis.expensetracker.controller;

import com.restapis.expensetracker.constant.Endpoint;
import com.restapis.expensetracker.entity.UserInfo;
import com.restapis.expensetracker.exception.RestException;
import com.restapis.expensetracker.model.user_info.UserInfoRequest;
import com.restapis.expensetracker.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(Endpoint.SIGN_UP)
    public ResponseEntity<UserInfoRequest> signup(@Valid @RequestBody UserInfoRequest userInfoRequest) throws RestException {
        return new ResponseEntity(authenticationService.signup(userInfoRequest), HttpStatus.CREATED);
    }
}
