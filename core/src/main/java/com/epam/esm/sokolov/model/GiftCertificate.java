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
@Table(name = "gift_certificate")
public class GiftCertificate {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private LocalDateTime createDate;
    private String createDateTimeZone;
    private LocalDateTime lastUpdateDate;
    private String lastUpdateDateTimeZone;
    private Integer duration;
    @ManyToMany(mappedBy = "giftCertificates", cascade = {CascadeType.ALL})
    private Set<Order> orders;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "tag_has_gift_certificate",
            joinColumns = @JoinColumn(name = "gift_certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftCertificate that = (GiftCertificate) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(price, that.price) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(createDateTimeZone, that.createDateTimeZone) &&
                Objects.equals(lastUpdateDate, that.lastUpdateDate) &&
                Objects.equals(lastUpdateDateTimeZone, that.lastUpdateDateTimeZone) &&
                Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, createDate, createDateTimeZone, lastUpdateDate, lastUpdateDateTimeZone, duration);
    }

    @Override
    public String toString() {
        return "GiftCertificate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", createDate=" + createDate +
                ", createDateTimeZone='" + createDateTimeZone + '\'' +
                ", lastUpdateDate=" + lastUpdateDate +
                ", lastUpdateDateTimeZone='" + lastUpdateDateTimeZone + '\'' +
                ", duration=" + duration +
                '}';
    }
}
