package com.epam.esm.sokolov.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
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
    @EqualsAndHashCode.Exclude
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "create_date_time_zone")
    private String createDateTimeZone;
    @EqualsAndHashCode.Exclude
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;
    @Column(name = "last_update_date_time_zone")
    private String lastUpdateDateTimeZone;
    @Column(name = "duration_in_days")
    private Integer durationInDays;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "giftCertificates", cascade = {CascadeType.MERGE})
    private Set<Order> orders;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "tag_has_gift_certificate",
            joinColumns = @JoinColumn(name = "gift_certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private String operation;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Column(name = "updated_at_time")
    private LocalDateTime updatedAtTime;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Column(name = "updated_at_time_zone")
    private String updatedAtTimeZone;

    @PreUpdate
    public void onPreUpdate() {
        this.operation = "UPDATE";
        this.updatedAtTime = LocalDateTime.now();
        this.updatedAtTimeZone = ZonedDateTime.now().getOffset().toString();
    }
}
