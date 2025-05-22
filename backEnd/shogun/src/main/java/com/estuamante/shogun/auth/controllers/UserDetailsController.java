package com.estuamante.shogun.auth.controllers;

import com.estuamante.shogun.auth.dtos.UserDetailsDto;
import com.estuamante.shogun.auth.entities.Authority;
import com.estuamante.shogun.auth.entities.User;
import com.estuamante.shogun.auth.mappers.AuthorityMapper;
import com.estuamante.shogun.auth.mappers.UserMapper;
import com.estuamante.shogun.auth.services.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
@AllArgsConstructor
public class UserDetailsController {
    private final CustomUserDetailsService userDetailsService;
    private final UserMapper userMapper;
    private final AuthorityMapper authorityMapper;
    @GetMapping("/profile")
    public ResponseEntity<UserDetailsDto> getUserProfile(Principal principal) {
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        UserDetailsDto userDetailsDto = userMapper.toDto(user);
        userDetailsDto.setAuthorities(authorityMapper.toDtoList((List<Authority>) user.getAuthorities()));
        return new ResponseEntity<>(userDetailsDto, HttpStatus.OK);
    }
}
