package com.epam.esm.sokolov.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "gift_certificate")
public class GiftCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "create_date_time_zone")
    private String createDateTimeZone;
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;
    @Column(name = "last_update_date_time_zone")
    private String lastUpdateDateTimeZone;
    private Integer duration;/*fixme in task field is: Duration - in days (expiration period).
                                    I have to create meaningful names for variables, for example durationInDays*/
    @ManyToMany(mappedBy = "giftCertificates", cascade = {CascadeType.MERGE})
    private Set<Order> orders;
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "tag_has_gift_certificate",
            joinColumns = @JoinColumn(name = "gift_certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;
    private String operation;
    @Column(name = "updated_at_time")
    private LocalDateTime updatedAtTime;
    @Column(name = "updated_at_time_zone")
    private String updatedAtTimeZone;

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

    @PreUpdate
    public void onPreUpdate() {
        this.operation = "UPDATE";
        this.updatedAtTime = LocalDateTime.now();
        this.updatedAtTimeZone = ZonedDateTime.now().getOffset().toString();
    }
}
