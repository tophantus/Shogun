package com.estuamante.shogun.auth.services;

import com.estuamante.shogun.auth.dtos.RegistrationRequest;
import com.estuamante.shogun.auth.dtos.RegistrationResponse;
import com.estuamante.shogun.auth.entities.User;
import com.estuamante.shogun.auth.helpers.VerificationCodeGenerator;
import com.estuamante.shogun.auth.mappers.UserMapper;
import com.estuamante.shogun.auth.repositories.AuthorityRepository;
import com.estuamante.shogun.auth.repositories.UserDetailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ServerErrorException;

import java.util.UUID;

@Service
@AllArgsConstructor
public class RegistrationService {
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;
    private UserDetailsRepository userDetailsRepository;
    private AuthorityService authorityService;
    private MailService mailService;

    public RegistrationResponse createUser(RegistrationRequest request) {
        User existing = userDetailsRepository.findByEmail(request.getEmail());

        if (existing != null) {
            return RegistrationResponse.builder()
                    .code(400)
                    .message("Email already exist")
                    .build();
        }

        try {
            User user = userMapper.toUser(request);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setEnabled(false);
            user.setProvider("manual");

            String code = VerificationCodeGenerator.generateCode();
            user.setVerificationCode(code);
            user.setAuthorities(authorityService.getUserAuthority());
            userDetailsRepository.save(user);

            //send email
            mailService.sendMail(user);

            return RegistrationResponse.builder()
                    .code(200)
                    .message("User created")
                    .build();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ServerErrorException(e.getMessage(), e.getCause());
        }
    }

    @Transactional
    public void deleteUser(String username) {
        userDetailsRepository.deleteByEmail(username);
    }

    public void verifyUser(String username) {
        User user = userDetailsRepository.findByEmail(username);
        user.setEnabled(true);
        userDetailsRepository.save(user);
    }
}
