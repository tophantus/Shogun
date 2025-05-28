package com.estuamante.shogun.mappers;

import com.estuamante.shogun.dtos.AddressDto;
import com.estuamante.shogun.dtos.AddressRequest;
import com.estuamante.shogun.entities.Address;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address requestToEntity(AddressRequest addressRequest);

    AddressDto entityToDto(Address address);

    List<AddressDto> entityListToDtoList(List<Address> addresses);

}
