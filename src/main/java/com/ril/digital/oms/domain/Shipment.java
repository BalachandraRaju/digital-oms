package com.ril.digital.oms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ril.digital.oms.domain.enumeration.ShipmentType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Shipment.
 */
@Entity
@Table(name = "shipment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Shipment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "dc_location")
    private String dcLocation;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ShipmentType type;

    @Column(name = "box_size")
    private String boxSize;

    @Column(name = "delivery")
    private String delivery;

    @Column(name = "tat_date")
    private LocalDate tatDate;

    @Column(name = "tah_hour_of_day")
    private Integer tahHourOfDay;

    @ManyToMany
    @JoinTable(
        name = "rel_shipment__order_item",
        joinColumns = @JoinColumn(name = "shipment_id"),
        inverseJoinColumns = @JoinColumn(name = "order_item_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "order", "shipments" }, allowSetters = true)
    private Set<OrderItem> orderItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Shipment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDcLocation() {
        return this.dcLocation;
    }

    public Shipment dcLocation(String dcLocation) {
        this.setDcLocation(dcLocation);
        return this;
    }

    public void setDcLocation(String dcLocation) {
        this.dcLocation = dcLocation;
    }

    public ShipmentType getType() {
        return this.type;
    }

    public Shipment type(ShipmentType type) {
        this.setType(type);
        return this;
    }

    public void setType(ShipmentType type) {
        this.type = type;
    }

    public String getBoxSize() {
        return this.boxSize;
    }

    public Shipment boxSize(String boxSize) {
        this.setBoxSize(boxSize);
        return this;
    }

    public void setBoxSize(String boxSize) {
        this.boxSize = boxSize;
    }

    public String getDelivery() {
        return this.delivery;
    }

    public Shipment delivery(String delivery) {
        this.setDelivery(delivery);
        return this;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public LocalDate getTatDate() {
        return this.tatDate;
    }

    public Shipment tatDate(LocalDate tatDate) {
        this.setTatDate(tatDate);
        return this;
    }

    public void setTatDate(LocalDate tatDate) {
        this.tatDate = tatDate;
    }

    public Integer getTahHourOfDay() {
        return this.tahHourOfDay;
    }

    public Shipment tahHourOfDay(Integer tahHourOfDay) {
        this.setTahHourOfDay(tahHourOfDay);
        return this;
    }

    public void setTahHourOfDay(Integer tahHourOfDay) {
        this.tahHourOfDay = tahHourOfDay;
    }

    public Set<OrderItem> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Shipment orderItems(Set<OrderItem> orderItems) {
        this.setOrderItems(orderItems);
        return this;
    }

    public Shipment addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.getShipments().add(this);
        return this;
    }

    public Shipment removeOrderItem(OrderItem orderItem) {
        this.orderItems.remove(orderItem);
        orderItem.getShipments().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Shipment)) {
            return false;
        }
        return id != null && id.equals(((Shipment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Shipment{" +
            "id=" + getId() +
            ", dcLocation='" + getDcLocation() + "'" +
            ", type='" + getType() + "'" +
            ", boxSize='" + getBoxSize() + "'" +
            ", delivery='" + getDelivery() + "'" +
            ", tatDate='" + getTatDate() + "'" +
            ", tahHourOfDay=" + getTahHourOfDay() +
            "}";
    }
}
