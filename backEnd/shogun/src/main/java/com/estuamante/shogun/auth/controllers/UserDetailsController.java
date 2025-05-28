package com.estuamante.shogun.auth.controllers;

import com.estuamante.shogun.auth.dtos.UserDetailsDto;
import com.estuamante.shogun.auth.services.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
@AllArgsConstructor
public class UserDetailsController {
    private final CustomUserDetailsService userDetailsService;

    @GetMapping("/profile")
    public ResponseEntity<UserDetailsDto> getUserProfile(Principal principal) {
        UserDetailsDto userDetailsDto =  userDetailsService.getUserDtoByUsername(principal.getName());
        if (userDetailsDto == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(userDetailsDto, HttpStatus.OK);
    }
}
