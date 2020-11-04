package com.epam.esm.sokolov.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigDecimal cost;
    private LocalDateTime createDate;
    private String createDateTimeZone;
    private LocalDateTime lastUpdateDate;
    private String lastUpdateDateTimeZone;
    @ManyToOne
    @JoinColumn(name = "user_account_id")
    private User user;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private Set<GiftCertificate> giftCertificates;

}
