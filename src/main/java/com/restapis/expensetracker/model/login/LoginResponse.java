package com.restapis.expensetracker.model.login;

import com.restapis.expensetracker.model.sign_up.UserInfoResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private UserInfoResponse userInfo;
}
