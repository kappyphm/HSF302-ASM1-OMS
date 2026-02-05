package crs.hsf302.assignment1.mapper;

import crs.hsf302.assignment1.domain.CreateOrderRequest;
import crs.hsf302.assignment1.domain.dto.CreateOrderDto;
import crs.hsf302.assignment1.domain.dto.OrderDto;
import crs.hsf302.assignment1.domain.dto.OrderSummaryDto;
import crs.hsf302.assignment1.domain.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public CreateOrderRequest fromDto(CreateOrderDto dto) {
        return CreateOrderRequest.builder()
                .email(dto.email())
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .countryCode(dto.countryCode())
                .phone(dto.phone())
                .city(dto.city())
                .address1(dto.address1())
                .address2(dto.address2())
                .region(dto.region())
                .postal(dto.postal())
                .build();
    }

    public OrderSummaryDto toSummary(Order order){
        OrderSummaryDto dto = new OrderSummaryDto();
        dto.setId(order.getId());
        dto.setCode(order.getCode());
        dto.setFullname(order.getFirstName()+" "+order.getLastName());
        dto.setCity(order.getCity());
        dto.setAddress(order.getAddress1());
        dto.setEmail(order.getEmail());

        return dto;

    }

    public OrderDto toDto(Order order){
        return new OrderDto(
                order.getId(),
                order.getCode(),
                order.getFirstName(),
                order.getLastName(),
                order.getEmail(),
                order.getPhone(),
                order.getAddress1(),
                order.getAddress2(),
                order.getCity(),
                order.getRegion(),
                order.getPostal(),
                order.getCountry().getName()
        );
    }
}
