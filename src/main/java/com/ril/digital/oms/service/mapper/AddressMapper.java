package com.ril.digital.oms.service.mapper;

import com.ril.digital.oms.domain.Address;
import com.ril.digital.oms.domain.Customer;
import com.ril.digital.oms.service.dto.AddressDTO;
import com.ril.digital.oms.service.dto.CustomerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring")
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerId")
    AddressDTO toDto(Address s);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);
}
