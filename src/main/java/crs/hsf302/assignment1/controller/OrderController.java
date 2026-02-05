package crs.hsf302.assignment1.controller;

import crs.hsf302.assignment1.exception.OrderNotFoundException;
import crs.hsf302.assignment1.mapper.OrderMapper;
import crs.hsf302.assignment1.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "order")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping(path = "/{orderId}")
   public String orderDetailView(Model model,  @PathVariable("orderId") Integer orderId){
        try {
            model.addAttribute("order", orderMapper.toDto(orderService.getOrderDetails(orderId)));
        }catch (OrderNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "error/404";
        }
       return "order-detail";
   }

}
