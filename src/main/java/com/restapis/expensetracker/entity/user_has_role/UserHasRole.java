package com.restapis.expensetracker.entity.user_has_role;

import com.restapis.expensetracker.entity.Audit;
import com.restapis.expensetracker.entity.Role;
import com.restapis.expensetracker.entity.UserInfo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserHasRole extends Audit {
    @EmbeddedId
    private UserHasRoleKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "assigned_by")
    private int assignedBy;
}
