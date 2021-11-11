package com.epam.esm.sokolov;

import com.epam.esm.sokolov.converter.DateConverter;
import com.epam.esm.sokolov.dto.GiftCertificateDTO;
import com.epam.esm.sokolov.dto.OrderDTO;
import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.model.Order;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@SpringBootApplication
public class Module3 {

    private final Converter<ZonedDateTime, LocalDateTime> fromZonedDateTimeToLocalDateTime = context -> DateConverter.getLocalDate(context.getSource());
    private final Converter<ZonedDateTime, String> fromZonedDateTimeToString = context -> context.getSource().getZone().toString();

    public static void main(String[] args) {
        SpringApplication.run(Module3.class, args);
    }

    @Bean
    @Primary
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        return modelMapper;
    }

    @Bean
    public ModelMapper mapperOrderToOrderDTO() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setPropertyCondition(context -> context.getSource() != null);
        modelMapper.createTypeMap(OrderDTO.class, Order.class)
                .addMappings(mapping -> mapping.using(fromZonedDateTimeToLocalDateTime).map(OrderDTO::getCreateDate, Order::setCreateDate))
                .addMappings(mapping -> mapping.using(fromZonedDateTimeToString).map(OrderDTO::getCreateDate, Order::setCreateDateTimeZone))
                .addMappings(mapping -> mapping.using(fromZonedDateTimeToLocalDateTime).map(OrderDTO::getLastUpdateDate, Order::setLastUpdateDate))
                .addMappings(mapping -> mapping.using(fromZonedDateTimeToString).map(OrderDTO::getLastUpdateDate, Order::setLastUpdateDateTimeZone));
        return modelMapper;
    }

    @Bean
    public ModelMapper mapperGiftCertificateDTOToGiftCertificate() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setPropertyCondition(context -> context.getSource() != null);
        modelMapper.createTypeMap(GiftCertificateDTO.class, GiftCertificate.class)
                .addMappings(mapping -> mapping.using(fromZonedDateTimeToLocalDateTime).map(GiftCertificateDTO::getCreateDate, GiftCertificate::setCreateDate))
                .addMappings(mapping -> mapping.using(fromZonedDateTimeToString).map(GiftCertificateDTO::getCreateDate, GiftCertificate::setCreateDateTimeZone))
                .addMappings(mapping -> mapping.using(fromZonedDateTimeToLocalDateTime).map(GiftCertificateDTO::getLastUpdateDate, GiftCertificate::setLastUpdateDate))
                .addMappings(mapping -> mapping.using(fromZonedDateTimeToString).map(GiftCertificateDTO::getLastUpdateDate, GiftCertificate::setLastUpdateDateTimeZone));
        return modelMapper;
    }

}
