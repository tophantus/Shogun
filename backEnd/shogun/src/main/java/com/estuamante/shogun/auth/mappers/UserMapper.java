package com.estuamante.shogun.auth.mappers;

import com.estuamante.shogun.auth.dtos.RegistrationRequest;
import com.estuamante.shogun.auth.dtos.UserDetailsDto;
import com.estuamante.shogun.auth.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",  uses = {AuthorityMapper.class})
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    User toUser(RegistrationRequest request);


    UserDetailsDto toDto(User user);
}
