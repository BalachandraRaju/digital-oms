package com.ril.digital.oms.service.mapper;

import com.ril.digital.oms.domain.Customer;
import com.ril.digital.oms.service.dto.CustomerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Customer} and its DTO {@link CustomerDTO}.
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {}
