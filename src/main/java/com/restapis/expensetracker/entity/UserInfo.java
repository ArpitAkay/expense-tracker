package com.restapis.expensetracker.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "user_infos")
@SQLDelete(
        sql = "UPDATE user_infos SET deleted_at = NOW() WHERE user_id = ?"
)
@Where(
        clause = "deleted_at IS NULL"
)
public class UserInfo extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private String name;

    private String phoneNumber;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isVerified = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "UserHasRole",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "userId"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "roleId"
            )
    )
    private Set<Role> roles;

    private String pin;
}