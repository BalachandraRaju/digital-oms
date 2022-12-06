package com.ril.digital.oms.repository;

import com.ril.digital.oms.domain.Shipment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ShipmentRepositoryWithBagRelationships {
    Optional<Shipment> fetchBagRelationships(Optional<Shipment> shipment);

    List<Shipment> fetchBagRelationships(List<Shipment> shipments);

    Page<Shipment> fetchBagRelationships(Page<Shipment> shipments);
}
