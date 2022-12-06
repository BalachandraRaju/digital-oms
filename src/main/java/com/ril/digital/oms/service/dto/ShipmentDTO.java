package com.ril.digital.oms.service.dto;

import com.ril.digital.oms.domain.enumeration.ShipmentType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.ril.digital.oms.domain.Shipment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShipmentDTO implements Serializable {

    private Long id;

    private String dcLocation;

    private ShipmentType type;

    private String boxSize;

    private String delivery;

    private LocalDate tatDate;

    private Integer tahHourOfDay;

    private Set<OrderItemDTO> orderItems = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDcLocation() {
        return dcLocation;
    }

    public void setDcLocation(String dcLocation) {
        this.dcLocation = dcLocation;
    }

    public ShipmentType getType() {
        return type;
    }

    public void setType(ShipmentType type) {
        this.type = type;
    }

    public String getBoxSize() {
        return boxSize;
    }

    public void setBoxSize(String boxSize) {
        this.boxSize = boxSize;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public LocalDate getTatDate() {
        return tatDate;
    }

    public void setTatDate(LocalDate tatDate) {
        this.tatDate = tatDate;
    }

    public Integer getTahHourOfDay() {
        return tahHourOfDay;
    }

    public void setTahHourOfDay(Integer tahHourOfDay) {
        this.tahHourOfDay = tahHourOfDay;
    }

    public Set<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShipmentDTO)) {
            return false;
        }

        ShipmentDTO shipmentDTO = (ShipmentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, shipmentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShipmentDTO{" +
            "id=" + getId() +
            ", dcLocation='" + getDcLocation() + "'" +
            ", type='" + getType() + "'" +
            ", boxSize='" + getBoxSize() + "'" +
            ", delivery='" + getDelivery() + "'" +
            ", tatDate='" + getTatDate() + "'" +
            ", tahHourOfDay=" + getTahHourOfDay() +
            ", orderItems=" + getOrderItems() +
            "}";
    }
}
