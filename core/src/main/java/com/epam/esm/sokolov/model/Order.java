package com.epam.esm.sokolov.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
//@EqualsAndHashCode
//@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_order")
public class Order {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal cost;
    private LocalDateTime createDate;
    private String createDateTimeZone;
    private LocalDateTime lastUpdateDate;
    private String lastUpdateDateTimeZone;
    @ManyToOne
    @JoinColumn(name = "user_account_id")
    private User user;
    @ManyToMany
    @JoinTable(
            name = "user_order_has_gift_certificate",
            joinColumns = @JoinColumn(name = "user_order_id"),
            inverseJoinColumns = @JoinColumn(name = "gift_certificate_id"))
    private Set<GiftCertificate> giftCertificates;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(cost, order.cost) &&
                Objects.equals(createDate, order.createDate) &&
                Objects.equals(createDateTimeZone, order.createDateTimeZone) &&
                Objects.equals(lastUpdateDate, order.lastUpdateDate) &&
                Objects.equals(lastUpdateDateTimeZone, order.lastUpdateDateTimeZone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cost, createDate, createDateTimeZone, lastUpdateDate, lastUpdateDateTimeZone);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", cost=" + cost +
                ", createDate=" + createDate +
                ", createDateTimeZone='" + createDateTimeZone + '\'' +
                ", lastUpdateDate=" + lastUpdateDate +
                ", lastUpdateDateTimeZone='" + lastUpdateDateTimeZone + '\'' +
                '}';
    }
}
