package com.restapis.expensetracker.service_impl;

import com.restapis.expensetracker.entity.Role;
import com.restapis.expensetracker.model.role.RoleResponse;
import com.restapis.expensetracker.repository.RoleRepository;
import com.restapis.expensetracker.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(
            RoleRepository roleRepository,
            ModelMapper modelMapper
    ) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        List<Role> roleSet = roleRepository.findAll();

        List<RoleResponse> roleResponses = roleSet.stream().map((role) -> modelMapper.map(role, RoleResponse.class)).collect(Collectors.toList());

        return roleResponses;
    }
}
