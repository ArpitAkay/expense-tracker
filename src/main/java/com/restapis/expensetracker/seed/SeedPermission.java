package com.restapis.expensetracker.seed;

import com.restapis.expensetracker.entity.Permission;
import com.restapis.expensetracker.repository.PermissionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SeedPermission {
    private static final String[] permissions = {"USER_INFO", "GET_ALL_ROLES", "GET_ALL_PERMISSIONS"};
    private final PermissionRepository permissionRepository;

    public SeedPermission(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @PostConstruct
    public void seedPermissions() {
        for(String permission : permissions) {
            System.out.println("Seeding permission: " + permission);
            Optional<Permission> permissionOptional = permissionRepository.findByName(permission);
            if(permissionOptional.isPresent()) {
                System.out.println("Permission already exists");
                continue;
            }
            Permission newPermission = new Permission();
            newPermission.setName(permission);
            permissionRepository.save(newPermission);
            System.out.println("Permission successfully seeded");
        }
    }
}
