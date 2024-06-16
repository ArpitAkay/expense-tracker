package com.restapis.expensetracker.model.reset_password;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResetPasswordRequest {
    @NotEmpty
    @Size(min = 8, max = 16, message = "Password must be between 8 and 16 characters long")
    private String password;
}
