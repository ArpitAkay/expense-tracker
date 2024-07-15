package com.restapis.expensetracker.model.send_otp_mail_again;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SendOtpMailAgainRequest {
    private String email;
}
