package com.restapis.expensetracker.model.verify_email;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VerifyEmailRequest {
    @Email
    private String email;
    @Size(min = 6, max = 6)
    private String otp;
}
