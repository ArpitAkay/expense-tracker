package com.restapis.expensetracker.model.renew_access_token;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RenewAccessTokenResponse {
    private String accessToken;
}
