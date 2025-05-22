package com.estuamante.shogun.auth.controllers;

import com.estuamante.shogun.auth.dtos.AuthorityRequest;
import com.estuamante.shogun.auth.entities.Authority;
import com.estuamante.shogun.auth.services.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/authority")
@CrossOrigin
@RequiredArgsConstructor
public class AuthorityController {

    private final AuthorityService authorityService;

    @PostMapping
    public ResponseEntity<Authority> createAuthority(
            @RequestBody AuthorityRequest request) {

        Authority authority = authorityService.createAuthority(
                request.getRole(),
                request.getDescription()
        );
        return ResponseEntity.ok(authority);
    }
}
