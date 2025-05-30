package com.estuamante.shogun.mappers;

import com.estuamante.shogun.dtos.OrderDetails;
import com.estuamante.shogun.dtos.OrderRequest;
import com.estuamante.shogun.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class, AddressMapper.class})
public interface OrderMapper {
    @Mapping(source = "shipmentTrackingNumber", target = "shipmentTrackingNumber", defaultValue = "")
    Order requestToEntity(OrderRequest orderRequest);

    OrderDetails entityToDetails(Order order);

    List<OrderDetails> entityListToDetailsList(List<Order> orders);

}
