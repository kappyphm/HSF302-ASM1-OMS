package crs.hsf302.assignment1.service;

import crs.hsf302.assignment1.domain.CreateOrderRequest;
import crs.hsf302.assignment1.domain.entity.Country;
import crs.hsf302.assignment1.domain.entity.Customer;
import crs.hsf302.assignment1.domain.entity.Order;
import crs.hsf302.assignment1.domain.entity.Program;
import crs.hsf302.assignment1.exception.CountryNotFoundException;
import crs.hsf302.assignment1.exception.DuplicateEmailException;
import crs.hsf302.assignment1.exception.OrderNotFoundException;
import crs.hsf302.assignment1.exception.ProgramNotFoundException;
import crs.hsf302.assignment1.repository.CountryRepo;
import crs.hsf302.assignment1.repository.CustomerRepository;
import crs.hsf302.assignment1.repository.OrderRepo;
import crs.hsf302.assignment1.repository.ProgramRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;
    private final ProgramRepo programRepo;
    private final CountryRepo countryRepo;
    private final CustomerRepository  customerRepo;

    @Transactional
    public void saveOrder(CreateOrderRequest req, UUID programId) {

        Program program = programRepo.findById(programId).orElseThrow(() -> new ProgramNotFoundException(programId.toString()));

        Country country = countryRepo.findById(req.countryCode()).orElseThrow(() -> new CountryNotFoundException(req.countryCode()));


        Customer customer = customerRepo.findByEmail(req.email()).orElse(new Customer(
                null,
                req.firstName(),
                req.lastName(),
                req.phone(),
                req.email(),
                null
        ));



        customerRepo.save(customer);

        Order newOrder = new Order();
        newOrder.setProgram(program);
        newOrder.setCountry(country);
        newOrder.setAddress1(req.address1());
        newOrder.setAddress2(req.address2());
        newOrder.setCity(req.city());
        newOrder.setCustomer(customer);
        newOrder.setPostal(req.postal());
        newOrder.setRegion(req.region());

        orderRepo.save(newOrder);

    }


    public Order getOrderDetails(Integer orderId) {
        return orderRepo.findById(orderId).orElseThrow(()->new OrderNotFoundException("Order với id "+orderId.toString()+" không tồn tại"));
    }

}
