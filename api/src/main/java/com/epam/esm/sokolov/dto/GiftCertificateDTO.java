package com.epam.esm.sokolov.dto;

import com.epam.esm.sokolov.converter.CustomDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificateDTO extends RepresentationModel<GiftCertificateDTO> {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private ZonedDateTime createDate;
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private ZonedDateTime lastUpdateDate;
    private Integer durationInDays;
    private Set<TagDTO> tags;

}
