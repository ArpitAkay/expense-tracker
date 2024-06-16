package com.restapis.expensetracker.controller;

import com.restapis.expensetracker.constant.Endpoint;
import com.restapis.expensetracker.model.permission.PermissionResponse;
import com.restapis.expensetracker.service.PermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Endpoint.BASE_URL)
public class PermissionController {
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PreAuthorize("hasAuthority('GET_ALL_PERMISSIONS')")
    @GetMapping(Endpoint.PERMISSIONS)
    public ResponseEntity<List<PermissionResponse>> getAllPermissions() {
        return new ResponseEntity<>(permissionService.getAllPermissions(), HttpStatus.OK);
    }
}
