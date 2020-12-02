package com.epam.esm.sokolov.dto;

import com.epam.esm.sokolov.converter.CustomDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "is mandatory")
    private String name;
    @NotBlank(message = "is mandatory")
    private String description;
    @Min(value = 0, message = "should be not a negative number")
    @Max(value = 99999, message = "should be not greater than 99999")
    private BigDecimal price;
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private ZonedDateTime createDate;
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private ZonedDateTime lastUpdateDate;
    @Min(value = 0, message = "should be not a negative number")
    @Max(value = 365, message = "should be not greater than 365")
    private Integer durationInDays;
    private Set<TagDTO> tags;

}
