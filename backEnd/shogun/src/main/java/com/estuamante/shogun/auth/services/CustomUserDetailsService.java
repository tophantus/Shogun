package com.estuamante.shogun.auth.services;

import com.estuamante.shogun.auth.entities.User;
import com.estuamante.shogun.auth.repositories.UserDetailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
    private UserDetailsRepository userDetailsRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDetailsRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username "+ username);
        }
        return user;
    }
}
