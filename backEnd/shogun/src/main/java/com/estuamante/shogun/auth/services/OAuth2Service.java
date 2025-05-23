package com.estuamante.shogun.auth.services;

import com.estuamante.shogun.auth.entities.User;
import com.estuamante.shogun.auth.repositories.UserDetailsRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class OAuth2Service {
    private final UserDetailsRepository userDetailsRepository;
    private final AuthorityService authorityService;

    public OAuth2Service(UserDetailsRepository userDetailsRepository, AuthorityService authorityService) {
        this.userDetailsRepository = userDetailsRepository;
        this.authorityService = authorityService;
    }

    public User getUser(String username) {
        return userDetailsRepository.findByEmail(username);
    }

    public User createUser(OAuth2User oAuth2User, String provider) {
        String firstName = oAuth2User.getAttribute("given_name");
        String lastName = oAuth2User.getAttribute("family_name");
        String email = oAuth2User.getAttribute("email");
        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .provider(provider)
                .enabled(true)
                .authorities(authorityService.getUserAuthority())
                .build();
        return userDetailsRepository.save(user);
    }
}
