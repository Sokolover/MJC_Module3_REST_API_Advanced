package com.epam.esm.sokolov.dto;

import com.epam.esm.sokolov.converter.CustomDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO extends RepresentationModel<UserDTO> {

    private Long id;
    private BigDecimal cost;
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private ZonedDateTime createDate;
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private ZonedDateTime lastUpdateDate;
    private UserDTO userDTO;
    private Set<GiftCertificateDTO> giftCertificateDTOs;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return Objects.equals(id, orderDTO.id) &&
                Objects.equals(cost, orderDTO.cost) &&
                Objects.equals(createDate, orderDTO.createDate) &&
                Objects.equals(lastUpdateDate, orderDTO.lastUpdateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, cost, createDate, lastUpdateDate);
    }
}
