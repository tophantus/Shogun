package com.estuamante.shogun.auth.dtos;

import com.estuamante.shogun.dtos.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private List<AuthorityDto> authorities;
    private List<AddressDto> addresses;
}
