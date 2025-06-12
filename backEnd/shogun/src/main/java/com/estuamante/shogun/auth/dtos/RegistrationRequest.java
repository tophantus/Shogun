package com.estuamante.shogun.auth.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private CharSequence password;
}
