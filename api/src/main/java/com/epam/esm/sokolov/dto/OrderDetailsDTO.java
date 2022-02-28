package com.epam.esm.sokolov.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDTO extends RepresentationModel<OrderDetailsDTO> {

    private BigDecimal cost;
    private ZonedDateTime createDate;
    private ZonedDateTime lastUpdateDate;

}
