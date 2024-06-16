package com.restapis.expensetracker.entity.user_has_role;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserHasRoleKey implements Serializable {
    @Column(name = "user_id")
    private int userId;

    @Column(name = "role_id")
    private int roleId;
}
