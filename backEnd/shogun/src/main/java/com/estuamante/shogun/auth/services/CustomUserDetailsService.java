package com.estuamante.shogun.auth.services;

import com.estuamante.shogun.auth.dtos.UserDetailsDto;
import com.estuamante.shogun.auth.entities.User;
import com.estuamante.shogun.auth.mappers.UserMapper;
import com.estuamante.shogun.auth.repositories.UserDetailsRepository;
import com.estuamante.shogun.repositories.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
    private final UserDetailsRepository userDetailsRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDetailsRepository.findWithAuthoritiesByEmail(username).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username "+ username);
        }
        userDetailsRepository.findWithAddressesByEmail(username);
        return user;
    }

    @Transactional(readOnly = true)
    public UserDetailsDto getUserDtoByUsername(String username) throws UsernameNotFoundException {
        User userWithAuthorities = userDetailsRepository.findWithAuthoritiesByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        userDetailsRepository.findWithAddressesByEmail(username);
        return userMapper.toDto(userWithAuthorities);
    }


}
