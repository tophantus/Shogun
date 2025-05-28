package com.estuamante.shogun.auth.mappers;

import com.estuamante.shogun.auth.dtos.RegistrationRequest;
import com.estuamante.shogun.auth.dtos.UserDetailsDto;
import com.estuamante.shogun.auth.entities.User;
import com.estuamante.shogun.mappers.AddressMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",  uses = {AuthorityMapper.class, AddressMapper.class})
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    User toUser(RegistrationRequest request);

    @Mapping(source = "authorityList", target = "authorities")
    UserDetailsDto toDto(User user);
}
