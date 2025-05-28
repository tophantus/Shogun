package com.estuamante.shogun.mappers;

import com.estuamante.shogun.dtos.OrderRequest;
import com.estuamante.shogun.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "shipmentTrackingNumber", target = "shipmentTrackingNumber", defaultValue = "")
    Order requestToEntity(OrderRequest orderRequest);
}
