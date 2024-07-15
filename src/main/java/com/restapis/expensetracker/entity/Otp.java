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
    private OtpType type;

    private String email;

    @Column(unique = true)
    private String otp;

    private Instant expiryTime;
}
