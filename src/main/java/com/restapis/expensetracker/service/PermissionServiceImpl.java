package com.restapis.expensetracker.service;

import com.restapis.expensetracker.entity.Permission;
import com.restapis.expensetracker.model.permission.PermissionResponse;
import com.restapis.expensetracker.repository.PermissionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    private final ModelMapper modelMapper;

    public PermissionServiceImpl(
            PermissionRepository permissionRepository,
            ModelMapper modelMapper) {
        this.permissionRepository = permissionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PermissionResponse> getAllPermissions() {
        List<Permission> permissionsList = permissionRepository.findAll();

        List<PermissionResponse> permissionResponseList = permissionsList.stream().map((permission) -> {
            return modelMapper.map(permission, PermissionResponse.class);
        }).collect(Collectors.toList());

        return permissionResponseList;
    }
}
