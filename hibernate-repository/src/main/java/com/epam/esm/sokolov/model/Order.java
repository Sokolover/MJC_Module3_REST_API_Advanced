package com.epam.esm.sokolov.model;

import com.epam.esm.sokolov.model.user.User;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@ToString(exclude = {"user", "giftCertificates", "operation", "createdAtTime", "createdAtTimeZone"})
@EqualsAndHashCode(exclude = {"createDate", "lastUpdateDate", "user", "giftCertificates", "operation", "createdAtTime", "createdAtTimeZone"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal cost;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "create_date_time_zone")
    private String createDateTimeZone;
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;
    @Column(name = "last_update_date_time_zone")
    private String lastUpdateDateTimeZone;
    @ManyToOne
    @JoinColumn(name = "user_account_id")
    private User user;
    @ManyToMany
    @JoinTable(name = "user_order_has_gift_certificate",
            joinColumns = @JoinColumn(name = "user_order_id"),
            inverseJoinColumns = @JoinColumn(name = "gift_certificate_id"))
    private Set<GiftCertificate> giftCertificates;
    private String operation;
    @Column(name = "created_at_time")
    private LocalDateTime createdAtTime;
    @Column(name = "created_at_time_zone")
    private String createdAtTimeZone;

    @PrePersist
    public void onPrePersist() {
        this.operation = "PERSIST";
        this.createdAtTime = LocalDateTime.now();
        this.createdAtTimeZone = ZonedDateTime.now().getOffset().toString();
    }
}
