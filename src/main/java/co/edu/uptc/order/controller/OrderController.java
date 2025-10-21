package co.edu.uptc.order.controller;

import co.edu.uptc.order.dto.OrderDTO;
import co.edu.uptc.order.dto.OrderDTOFromFE;
import co.edu.uptc.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    OrderService orderService;

    @PostMapping("/saveOrder")
    public ResponseEntity<OrderDTO> saveOrder(@RequestBody OrderDTOFromFE orderDetails){
        OrderDTO orderSaveInDB = orderService.saveOrderInDB(orderDetails);
        return new ResponseEntity<>(orderSaveInDB, HttpStatus.CREATED);
    }
}
