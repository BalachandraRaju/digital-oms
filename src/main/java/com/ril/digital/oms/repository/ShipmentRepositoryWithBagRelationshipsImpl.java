package com.ril.digital.oms.repository;

import com.ril.digital.oms.domain.Shipment;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ShipmentRepositoryWithBagRelationshipsImpl implements ShipmentRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Shipment> fetchBagRelationships(Optional<Shipment> shipment) {
        return shipment.map(this::fetchOrderItems);
    }

    @Override
    public Page<Shipment> fetchBagRelationships(Page<Shipment> shipments) {
        return new PageImpl<>(fetchBagRelationships(shipments.getContent()), shipments.getPageable(), shipments.getTotalElements());
    }

    @Override
    public List<Shipment> fetchBagRelationships(List<Shipment> shipments) {
        return Optional.of(shipments).map(this::fetchOrderItems).orElse(Collections.emptyList());
    }

    Shipment fetchOrderItems(Shipment result) {
        return entityManager
            .createQuery(
                "select shipment from Shipment shipment left join fetch shipment.orderItems where shipment is :shipment",
                Shipment.class
            )
            .setParameter("shipment", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Shipment> fetchOrderItems(List<Shipment> shipments) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, shipments.size()).forEach(index -> order.put(shipments.get(index).getId(), index));
        List<Shipment> result = entityManager
            .createQuery(
                "select distinct shipment from Shipment shipment left join fetch shipment.orderItems where shipment in :shipments",
                Shipment.class
            )
            .setParameter("shipments", shipments)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
