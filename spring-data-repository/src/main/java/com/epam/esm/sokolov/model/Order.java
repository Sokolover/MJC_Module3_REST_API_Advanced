package com.epam.esm.sokolov.model;

import com.epam.esm.sokolov.model.user.User;
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
@Table(name = "user_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal cost;
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
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "user_account_id")
    private User user;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(
            name = "user_order_has_gift_certificate",
            joinColumns = @JoinColumn(name = "user_order_id"),
            inverseJoinColumns = @JoinColumn(name = "gift_certificate_id"))
    private Set<GiftCertificate> giftCertificates;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private String operation;
    @Column(name = "created_at_time")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private LocalDateTime createdAtTime;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Column(name = "created_at_time_zone")
    private String createdAtTimeZone;

    @PrePersist
    public void onPrePersist() {
        this.operation = "PERSIST";
        this.createdAtTime = LocalDateTime.now();
        this.createdAtTimeZone = ZonedDateTime.now().getOffset().toString();
    }
}
