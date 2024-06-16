package com.restapis.expensetracker.model.sign_up;

import com.restapis.expensetracker.model.role.RoleResponse;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserInfoResponse {
    private int id;
    private String name;
    private String phoneNumber;
    private String email;
    private Set<RoleResponse> roles;
}
