package com.ril.digital.oms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ril.digital.oms.domain.enumeration.ArticleType;
import com.ril.digital.oms.domain.enumeration.OrderItemStatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OrderItem.
 */
@Entity
@Table(name = "order_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "article")
    private String article;

    @Enumerated(EnumType.STRING)
    @Column(name = "article_type")
    private ArticleType articleType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderItemStatus status;

    @Column(name = "tat_date")
    private LocalDate tatDate;

    @Column(name = "tah_hour_of_day")
    private Integer tahHourOfDay;

    @ManyToOne
    @JsonIgnoreProperties(value = { "orderItems", "customer", "address" }, allowSetters = true)
    private Order order;

    @ManyToMany(mappedBy = "orderItems")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "orderItems" }, allowSetters = true)
    private Set<Shipment> shipments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrderItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArticle() {
        return this.article;
    }

    public OrderItem article(String article) {
        this.setArticle(article);
        return this;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public ArticleType getArticleType() {
        return this.articleType;
    }

    public OrderItem articleType(ArticleType articleType) {
        this.setArticleType(articleType);
        return this;
    }

    public void setArticleType(ArticleType articleType) {
        this.articleType = articleType;
    }

    public OrderItemStatus getStatus() {
        return this.status;
    }

    public OrderItem status(OrderItemStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(OrderItemStatus status) {
        this.status = status;
    }

    public LocalDate getTatDate() {
        return this.tatDate;
    }

    public OrderItem tatDate(LocalDate tatDate) {
        this.setTatDate(tatDate);
        return this;
    }

    public void setTatDate(LocalDate tatDate) {
        this.tatDate = tatDate;
    }

    public Integer getTahHourOfDay() {
        return this.tahHourOfDay;
    }

    public OrderItem tahHourOfDay(Integer tahHourOfDay) {
        this.setTahHourOfDay(tahHourOfDay);
        return this;
    }

    public void setTahHourOfDay(Integer tahHourOfDay) {
        this.tahHourOfDay = tahHourOfDay;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderItem order(Order order) {
        this.setOrder(order);
        return this;
    }

    public Set<Shipment> getShipments() {
        return this.shipments;
    }

    public void setShipments(Set<Shipment> shipments) {
        if (this.shipments != null) {
            this.shipments.forEach(i -> i.removeOrderItem(this));
        }
        if (shipments != null) {
            shipments.forEach(i -> i.addOrderItem(this));
        }
        this.shipments = shipments;
    }

    public OrderItem shipments(Set<Shipment> shipments) {
        this.setShipments(shipments);
        return this;
    }

    public OrderItem addShipment(Shipment shipment) {
        this.shipments.add(shipment);
        shipment.getOrderItems().add(this);
        return this;
    }

    public OrderItem removeShipment(Shipment shipment) {
        this.shipments.remove(shipment);
        shipment.getOrderItems().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderItem)) {
            return false;
        }
        return id != null && id.equals(((OrderItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderItem{" +
            "id=" + getId() +
            ", article='" + getArticle() + "'" +
            ", articleType='" + getArticleType() + "'" +
            ", status='" + getStatus() + "'" +
            ", tatDate='" + getTatDate() + "'" +
            ", tahHourOfDay=" + getTahHourOfDay() +
            "}";
    }
}
