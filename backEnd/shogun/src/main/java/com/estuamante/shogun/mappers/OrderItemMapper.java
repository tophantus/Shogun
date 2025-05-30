package com.estuamante.shogun.mappers;

import com.estuamante.shogun.dtos.OrderItemDetails;
import com.estuamante.shogun.entities.OrderItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface OrderItemMapper {

    OrderItemDetails entityToDetails(OrderItem orderItem);

    List<OrderItemDetails> entityListToDetailsList(List<OrderItem> orderItems);
}
