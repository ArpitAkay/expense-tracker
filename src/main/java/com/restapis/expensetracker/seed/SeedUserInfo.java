package com.restapis.expensetracker.seed;

import com.restapis.expensetracker.entity.Role;
import com.restapis.expensetracker.entity.UserInfo;
import com.restapis.expensetracker.repository.RoleRepository;
import com.restapis.expensetracker.repository.UserInfoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class SeedUserInfo {
    private final String[] adminRoles = {"ROLE_ADMIN"};
    private final UserInfoRepository userInfoRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public SeedUserInfo(
            UserInfoRepository userInfoRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userInfoRepository = userInfoRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void seedUserInfo() {
        System.out.println("Seeding user info");

        Optional<UserInfo> userInfoOptional = userInfoRepository.findByEmail("ak2400320@gmail.com");

        if(userInfoOptional.isEmpty()) {
            Set<Role> roles = new HashSet<>();

            for (String role : adminRoles) {
                Optional<Role> roleOptional = roleRepository.findByName(role);

                if (roleOptional.isPresent()) {
                    roles.add(roleOptional.get());
                } else {
                    throw new RuntimeException(
                            String.format("Role %s not found", role)
                    );
                }
            }

            UserInfo userInfo = new UserInfo();
            userInfo.setName("Arpit Kumar");
            userInfo.setEmail("ak2400320@gmail.com");
            userInfo.setPhoneNumber("+916397473077");
            userInfo.setPassword(passwordEncoder.encode("12345678"));
            userInfo.setRoles(roles);

            userInfoRepository.save(userInfo);
            System.out.println("User info successfully seeded");
        } else {
            System.out.println("User info already seeded");
        }
    }
}
