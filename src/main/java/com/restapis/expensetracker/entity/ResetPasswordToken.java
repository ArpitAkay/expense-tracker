package com.restapis.expensetracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "reset_password_tokens")
public class ResetPasswordToken extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int resetPasswordTokenId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    private UUID token;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isUsed = false;

    @Column(nullable = false)
    private Instant expiryTime;
}
