package com.ril.digital.oms.service.mapper;

import com.ril.digital.oms.domain.OrderItem;
import com.ril.digital.oms.domain.Shipment;
import com.ril.digital.oms.service.dto.OrderItemDTO;
import com.ril.digital.oms.service.dto.ShipmentDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Shipment} and its DTO {@link ShipmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface ShipmentMapper extends EntityMapper<ShipmentDTO, Shipment> {
    @Mapping(target = "orderItems", source = "orderItems", qualifiedByName = "orderItemIdSet")
    ShipmentDTO toDto(Shipment s);

    @Mapping(target = "removeOrderItem", ignore = true)
    Shipment toEntity(ShipmentDTO shipmentDTO);

    @Named("orderItemId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrderItemDTO toDtoOrderItemId(OrderItem orderItem);

    @Named("orderItemIdSet")
    default Set<OrderItemDTO> toDtoOrderItemIdSet(Set<OrderItem> orderItem) {
        return orderItem.stream().map(this::toDtoOrderItemId).collect(Collectors.toSet());
    }
}
