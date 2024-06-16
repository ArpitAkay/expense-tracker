package com.restapis.expensetracker.model.login;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @Email
    @NotNull
    @NotBlank
    @NotEmpty
    private String email;
    @Size(min = 8, max = 16, message = "Password must be between 8 and 16 characters long")
    private String password;
}
