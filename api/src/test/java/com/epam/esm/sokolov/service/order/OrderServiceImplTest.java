package com.epam.esm.sokolov.service.order;

import com.epam.esm.sokolov.converter.GiftCertificateConverter;
import com.epam.esm.sokolov.converter.OrderConverter;
import com.epam.esm.sokolov.converter.TagConverter;
import com.epam.esm.sokolov.converter.UserConverter;
import com.epam.esm.sokolov.dto.GiftCertificateDTO;
import com.epam.esm.sokolov.dto.OrderDTO;
import com.epam.esm.sokolov.dto.UserDTO;
import com.epam.esm.sokolov.exception.ServiceException;
import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.model.Order;
import com.epam.esm.sokolov.repository.certificate.GiftCertificateRepositoryImpl;
import com.epam.esm.sokolov.repository.order.OrderRepositoryImpl;
import com.epam.esm.sokolov.service.certificate.GiftCertificateMapperImpl;
import com.epam.esm.sokolov.service.certificate.GiftCertificateServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock
    private OrderRepositoryImpl orderRepository;
    @Mock
    private GiftCertificateRepositoryImpl giftCertificateRepository;
    @Spy
    @InjectMocks
    private OrderConverter orderConverter = new OrderConverter();
    @Spy
    private GiftCertificateConverter giftCertificateConverter;
    @Spy
    private UserConverter userConverter;
    @Spy
    private TagConverter tagConverter;

    @Test
    void save() {//todo ask about testing when we have ZonedDateTime.now() in service
        OrderDTO orderDTOFromController = createOrderDTOFromController();
        Order orderFromController = orderConverter.convert(orderDTOFromController);
        List<Long> giftCertificateIds = orderFromController.getGiftCertificates()
                .stream()
                .map(GiftCertificate::getId)
                .collect(Collectors.toList());
        Mockito.doAnswer(invocation -> orderFromController).when(orderRepository).save(orderFromController);
        Mockito.doReturn(getGiftCertificates()).when(giftCertificateRepository)
                .findAllById(giftCertificateIds);
        OrderDTO savedOrderDTOFromController = orderService.save(orderDTOFromController);
        Assertions.assertEquals(new OrderDTO(), savedOrderDTOFromController);
    }

    private OrderDTO createOrderDTOFromController(){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCreateDate(ZonedDateTime.now());
        orderDTO.setLastUpdateDate(ZonedDateTime.now());
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        orderDTO.setUserDTO(userDTO);
        Set<GiftCertificateDTO> giftCertificateDTOS = getGiftCertificateDTOS();
        orderDTO.setGiftCertificateDTOs(giftCertificateDTOS);
        return orderDTO;
    }

    private Set<GiftCertificateDTO> getGiftCertificateDTOS() {
        Set<GiftCertificateDTO> giftCertificateDTOS = new HashSet<>();
        GiftCertificateDTO giftCertificateDTO1 = new GiftCertificateDTO();
        giftCertificateDTO1.setId(1L);
        GiftCertificateDTO giftCertificateDTO2 = new GiftCertificateDTO();
        giftCertificateDTO1.setId(2L);
        giftCertificateDTOS.add(giftCertificateDTO1);
        giftCertificateDTOS.add(giftCertificateDTO2);
        return giftCertificateDTOS;
    }

    private List<GiftCertificate> getGiftCertificates() {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        GiftCertificate giftCertificate1 = new GiftCertificate();
        giftCertificate1.setId(1L);
        giftCertificate1.setPrice(new BigDecimal("1"));
        giftCertificates.add(giftCertificate1);
        GiftCertificate giftCertificate2 = new GiftCertificate();
        giftCertificate2.setId(2L);
        giftCertificate2.setPrice(new BigDecimal("2"));
        giftCertificates.add(giftCertificate2);
        return giftCertificates;
    }
}