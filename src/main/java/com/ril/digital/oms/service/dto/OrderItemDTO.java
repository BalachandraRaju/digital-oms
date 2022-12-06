package com.ril.digital.oms.service.dto;

import com.ril.digital.oms.domain.enumeration.ArticleType;
import com.ril.digital.oms.domain.enumeration.OrderItemStatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.ril.digital.oms.domain.OrderItem} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderItemDTO implements Serializable {

    private Long id;

    private String article;

    private ArticleType articleType;

    private OrderItemStatus status;

    private LocalDate tatDate;

    private Integer tahHourOfDay;

    private OrderDTO order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public ArticleType getArticleType() {
        return articleType;
    }

    public void setArticleType(ArticleType articleType) {
        this.articleType = articleType;
    }

    public OrderItemStatus getStatus() {
        return status;
    }

    public void setStatus(OrderItemStatus status) {
        this.status = status;
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

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderItemDTO)) {
            return false;
        }

        OrderItemDTO orderItemDTO = (OrderItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, orderItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderItemDTO{" +
            "id=" + getId() +
            ", article='" + getArticle() + "'" +
            ", articleType='" + getArticleType() + "'" +
            ", status='" + getStatus() + "'" +
            ", tatDate='" + getTatDate() + "'" +
            ", tahHourOfDay=" + getTahHourOfDay() +
            ", order=" + getOrder() +
            "}";
    }
}
