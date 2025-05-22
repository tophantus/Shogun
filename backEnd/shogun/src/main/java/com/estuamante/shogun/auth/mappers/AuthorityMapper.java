package com.estuamante.shogun.auth.mappers;

import com.estuamante.shogun.auth.dtos.AuthorityDto;
import com.estuamante.shogun.auth.entities.Authority;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorityMapper {
    AuthorityDto toDto(Authority authority);

    List<AuthorityDto> toDtoList(List<Authority> authorities);
}
