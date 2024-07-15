package com.restapis.expensetracker.controller;

import com.restapis.expensetracker.constant.Endpoint;
import com.restapis.expensetracker.exception.RestException;
import com.restapis.expensetracker.model.sign_up.UserInfoResponse;
import com.restapis.expensetracker.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Endpoint.BASE_URL)
public class UserInfoController {
    private final UserService userService;

    public UserInfoController(
            UserService userService
    ) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('USER_INFO')")
    @GetMapping(Endpoint.USER_INFO)
    public ResponseEntity<UserInfoResponse> getUserInfo(@PathVariable int userId) throws RestException {
        return new ResponseEntity<>(userService.getUserInfo(userId), HttpStatus.OK);
    }
}
