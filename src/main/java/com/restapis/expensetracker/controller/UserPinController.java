package com.restapis.expensetracker.controller;

import com.restapis.expensetracker.constant.Endpoint;
import com.restapis.expensetracker.exception.RestException;
import com.restapis.expensetracker.model.api.ApiResponse;
import com.restapis.expensetracker.model.user_pin.UserPinRequest;
import com.restapis.expensetracker.service.UserPinService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.BASE_URL)
public class UserPinController {
    private final UserPinService userPinService;

    public UserPinController(
            UserPinService userPinService
    ) {
        this.userPinService = userPinService;
    }

    @PostMapping(Endpoint.ADD_USER_PIN)
    public ResponseEntity<ApiResponse> addedUserPin(
            @PathVariable int userId,
            @Valid @RequestBody UserPinRequest userPinRequest
    ) throws RestException {
        return new ResponseEntity<>(userPinService.addedUserPin(userId, userPinRequest), HttpStatus.OK);
    }

    @PostMapping(Endpoint.VERIFY_USER_PIN)
    public ResponseEntity<ApiResponse> verifyUserPin(
            @PathVariable int userId,
            @Valid @RequestBody UserPinRequest userPinRequest
    ) throws RestException {
        return new ResponseEntity<>(userPinService.verifyUserPin(userId, userPinRequest), HttpStatus.OK);
    }
}
