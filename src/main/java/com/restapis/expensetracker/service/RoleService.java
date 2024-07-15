package com.restapis.expensetracker.service;

import com.restapis.expensetracker.model.role.RoleResponse;

import java.util.List;

public interface RoleService {
    List<RoleResponse> getAllRoles();
}
