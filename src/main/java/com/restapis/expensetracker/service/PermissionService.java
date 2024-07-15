package com.restapis.expensetracker.service;

import com.restapis.expensetracker.model.permission.PermissionResponse;

import java.util.List;

public interface PermissionService {
    List<PermissionResponse> getAllPermissions();
}
