package com.restapis.expensetracker.model.send_verification_mail_again;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SendVerificationMailAgainRequest {
    private String email;
}
