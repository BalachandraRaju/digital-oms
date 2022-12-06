package com.ril.digital.oms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "line_1")
    private String line1;

    @Column(name = "line_2")
    private String line2;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "pincode")
    private Long pincode;

    @Column(name = "lat")
    private Float lat;

    @Column(name = "lon")
    private Float lon;

    @Column(name = "country")
    private String country;

    @Column(name = "landmart")
    private String landmart;

    @OneToMany(mappedBy = "address")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "orderItems", "customer", "address" }, allowSetters = true)
    private Set<Order> orders = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "orders", "addresses" }, allowSetters = true)
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Address id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLine1() {
        return this.line1;
    }

    public Address line1(String line1) {
        this.setLine1(line1);
        return this;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return this.line2;
    }

    public Address line2(String line2) {
        this.setLine2(line2);
        return this;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getCity() {
        return this.city;
    }

    public Address city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public Address state(String state) {
        this.setState(state);
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getPincode() {
        return this.pincode;
    }

    public Address pincode(Long pincode) {
        this.setPincode(pincode);
        return this;
    }

    public void setPincode(Long pincode) {
        this.pincode = pincode;
    }

    public Float getLat() {
        return this.lat;
    }

    public Address lat(Float lat) {
        this.setLat(lat);
        return this;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLon() {
        return this.lon;
    }

    public Address lon(Float lon) {
        this.setLon(lon);
        return this;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }

    public String getCountry() {
        return this.country;
    }

    public Address country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLandmart() {
        return this.landmart;
    }

    public Address landmart(String landmart) {
        this.setLandmart(landmart);
        return this;
    }

    public void setLandmart(String landmart) {
        this.landmart = landmart;
    }

    public Set<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(Set<Order> orders) {
        if (this.orders != null) {
            this.orders.forEach(i -> i.setAddress(null));
        }
        if (orders != null) {
            orders.forEach(i -> i.setAddress(this));
        }
        this.orders = orders;
    }

    public Address orders(Set<Order> orders) {
        this.setOrders(orders);
        return this;
    }

    public Address addOrder(Order order) {
        this.orders.add(order);
        order.setAddress(this);
        return this;
    }

    public Address removeOrder(Order order) {
        this.orders.remove(order);
        order.setAddress(null);
        return this;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Address customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        return id != null && id.equals(((Address) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", line1='" + getLine1() + "'" +
            ", line2='" + getLine2() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", pincode=" + getPincode() +
            ", lat=" + getLat() +
            ", lon=" + getLon() +
            ", country='" + getCountry() + "'" +
            ", landmart='" + getLandmart() + "'" +
            "}";
    }
}
