package crs.hsf302.assignment1.mapper;

import crs.hsf302.assignment1.domain.CreateCustomerRequest;
import crs.hsf302.assignment1.domain.dto.CreateOrderDto;
import crs.hsf302.assignment1.domain.dto.CustomerDto;
import crs.hsf302.assignment1.domain.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CreateCustomerRequest fromDto(CreateOrderDto dto){
        return new CreateCustomerRequest(
                dto.firstName(),
                dto.lastName(),
                dto.email(),
                dto.phone()
        );
    }

    public CustomerDto toDto(Customer customer){
        return new CustomerDto(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhone()
        );
    }



}
