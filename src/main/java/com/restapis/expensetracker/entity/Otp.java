package com.restapis.expensetracker.entity;

import com.restapis.expensetracker.enumeration.OtpType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "otps")
public class Otp extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int otpId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OtpType type;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    private String otp;

    @Column(nullable = false)
    private Instant expiryTime;
}
