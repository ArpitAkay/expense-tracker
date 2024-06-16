package com.restapis.expensetracker.controller;

import com.restapis.expensetracker.constant.Endpoint;
import com.restapis.expensetracker.model.role.RoleResponse;
import com.restapis.expensetracker.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(Endpoint.BASE_URL)
public class RoleController {
    private final RoleService roleService;

    public RoleController(
            RoleService roleService
    ) {
        this.roleService = roleService;
    }

    @PreAuthorize("hasAuthority('GET_ALL_ROLES')")
    @GetMapping(Endpoint.ROLES)
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }
}
