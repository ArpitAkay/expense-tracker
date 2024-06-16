package com.restapis.expensetracker.seed;

import com.restapis.expensetracker.entity.Permission;
import com.restapis.expensetracker.repository.PermissionRepository;
import com.restapis.expensetracker.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class SeedRole {
    private static final String[] roles = {"ROLE_ADMIN", "ROLE_NORMAL"};
    private static final String[] adminPermissions = {"USER_INFO", "GET_ALL_ROLES", "GET_ALL_PERMISSIONS"};
    private static final String[] normalPermissions = {"USER_INFO"};
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public SeedRole(
            RoleRepository roleRepository,
                PermissionRepository permissionRepository
    ) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    private Set<Permission> rolePermissions(String[] permissions) {
        Set<Permission> permissionSet = new HashSet<>();

        for(String permission: permissions) {
            Optional<Permission> permissionOptional = permissionRepository.findByName(permission);

            if(permissionOptional.isPresent()) {
                permissionSet.add(permissionOptional.get());
            } else {
                Permission newPermission = new Permission();
                newPermission.setName(permission);
                Permission savedPermission = permissionRepository.save(newPermission);
                permissionSet.add(savedPermission);
            }
        }

        return permissionSet;
    }

    public void seedRolesWithPermissions(String role, Set<Permission> permissionSet) {
            System.out.println("Seeding role: " + role);

            Optional<com.restapis.expensetracker.entity.Role> roleOptional = roleRepository.findByName(role);

            if(roleOptional.isPresent()) {
                System.out.println("Role already exists");
                com.restapis.expensetracker.entity.Role existingRole = roleOptional.get();
                existingRole.setPermissions(permissionSet);
                roleRepository.save(existingRole);
                System.out.println("Role successfully updated");
            } else {
                com.restapis.expensetracker.entity.Role newRole = new com.restapis.expensetracker.entity.Role();
                newRole.setName(role);
                System.out.println(permissionSet);
                newRole.setPermissions(permissionSet);
                roleRepository.save(newRole);
                System.out.println("Role successfully seeded");
            }
    }

    @PostConstruct
    public void seedRoles() {
        Set<Permission> adminPermissionSet = rolePermissions(adminPermissions);
        Set<Permission> normalPermissionSet = rolePermissions(normalPermissions);

        seedRolesWithPermissions(roles[0], adminPermissionSet);
        seedRolesWithPermissions(roles[1], normalPermissionSet);
    }
}
