package com.restapis.expensetracker.model.role;

import com.restapis.expensetracker.model.permission.PermissionResponse;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoleResponse {
    private int id;
    private String name;
    private Set<PermissionResponse> permissions;
}
