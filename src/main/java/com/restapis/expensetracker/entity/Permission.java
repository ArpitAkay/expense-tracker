package com.restapis.expensetracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"roles"})
@Table(name = "permissions")
public class Permission extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int permissionId;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;
}
