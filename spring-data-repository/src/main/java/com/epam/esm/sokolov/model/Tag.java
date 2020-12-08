package com.epam.esm.sokolov.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "tags", cascade = {CascadeType.MERGE})
    private Set<GiftCertificate> giftCertificates;

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
