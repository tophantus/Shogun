package com.estuamante.shogun.auth.controllers;

import com.estuamante.shogun.auth.config.JwtTokenHelper;
import com.estuamante.shogun.auth.dtos.LoginRequest;
import com.estuamante.shogun.auth.dtos.RegistrationRequest;
import com.estuamante.shogun.auth.dtos.RegistrationResponse;
import com.estuamante.shogun.auth.dtos.UserToken;
import com.estuamante.shogun.auth.entities.User;
import com.estuamante.shogun.auth.services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
    private AuthenticationManager authenticationManager;
    private RegistrationService registrationService;
    private UserDetailsService userDetailsService;
    private JwtTokenHelper jwtTokenHelper;

    @PostMapping("/login")
    public ResponseEntity<UserToken> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            );

            Authentication authenticationResponse = this.authenticationManager.authenticate(authentication);

            if (authenticationResponse.isAuthenticated()) {
                User user = (User) authenticationResponse.getPrincipal();
                if (!user.isEnabled()) {
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }

                String token = jwtTokenHelper.generateToken(user.getEmail());
                UserToken userToken = UserToken.builder().token(token).build();
                return new ResponseEntity<>(userToken, HttpStatus.OK);
            }
        } catch (BadCredentialsException | DisabledException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest request) {
        RegistrationResponse response = registrationService.createUser(request);
        return new ResponseEntity<>(
                response,
                response.getCode() == 200 ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST
        );
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String code = map.get("code");

        User user = (User) userDetailsService.loadUserByUsername(username);

        if (user != null && user.getVerificationCode().equals(code)) {
            registrationService.verifyUser(username);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/delete/{username}")
    public ResponseEntity<Void> delete(@PathVariable("username")String username) {
        registrationService.deleteUser(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
