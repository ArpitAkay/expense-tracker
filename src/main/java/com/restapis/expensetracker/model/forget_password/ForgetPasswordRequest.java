package com.restapis.expensetracker.model.forget_password;

import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ForgetPasswordRequest {
    @Email
    private String email;
}
