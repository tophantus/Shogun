package com.estuamante.shogun.services;

import com.estuamante.shogun.auth.entities.User;
import com.estuamante.shogun.auth.services.CustomUserDetailsService;
import com.estuamante.shogun.dtos.AddressRequest;
import com.estuamante.shogun.entities.Address;
import com.estuamante.shogun.mappers.AddressMapper;
import com.estuamante.shogun.repositories.AddressRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class AddressService {
    private final CustomUserDetailsService userDetailsService;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public AddressService(
            CustomUserDetailsService userDetailsService,
            AddressRepository addressRepository,
            AddressMapper addressMapper
    ) {
        this.userDetailsService = userDetailsService;
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    public Address createAddress(AddressRequest addressRequest, Principal principal) {
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        Address address = addressMapper.requestToEntity(addressRequest);
        address.setUser(user);
        return addressRepository.save(address);
    }

}
