package com.ril.digital.oms.service.mapper;

import com.ril.digital.oms.domain.Address;
import com.ril.digital.oms.domain.Customer;
import com.ril.digital.oms.domain.Order;
import com.ril.digital.oms.service.dto.AddressDTO;
import com.ril.digital.oms.service.dto.CustomerDTO;
import com.ril.digital.oms.service.dto.OrderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Order} and its DTO {@link OrderDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerId")
    @Mapping(target = "address", source = "address", qualifiedByName = "addressId")
    OrderDTO toDto(Order s);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);

    @Named("addressId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AddressDTO toDtoAddressId(Address address);
}
