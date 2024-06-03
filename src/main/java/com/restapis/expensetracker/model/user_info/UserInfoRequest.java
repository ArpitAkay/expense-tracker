package com.restapis.expensetracker.model.user_info;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoRequest {
    private int id;
    @NotEmpty
    @NotBlank
    private String name;
    @NotEmpty
    @NotBlank
    private String phoneNumber;
    @Email
    private String email;
    @NotEmpty
    @Size(min = 8, max = 16, message = "Password must be between 8 and 16 characters long")
    private String password;
}
