package com.restapis.expensetracker.model.user_pin;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPinRequest {
    @Size(min = 4, max = 4)
    private String pin;
}
