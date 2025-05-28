package com.estuamante.shogun.controllers;

import com.estuamante.shogun.dtos.AddressRequest;
import com.estuamante.shogun.entities.Address;
import com.estuamante.shogun.services.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/api/address")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody AddressRequest addressRequest, Principal principal) {
        Address address = addressService.createAddress(addressRequest, principal);
        return new ResponseEntity<>(address, HttpStatus.CREATED);
    }
}
