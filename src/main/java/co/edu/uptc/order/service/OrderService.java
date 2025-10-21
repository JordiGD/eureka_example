package co.edu.uptc.order.service;

import co.edu.uptc.order.dto.OrderDTO;
import co.edu.uptc.order.dto.OrderDTOFromFE;
import co.edu.uptc.order.dto.UserDTO;
import co.edu.uptc.order.entity.Order;
import co.edu.uptc.order.mapper.OrderMapper;
import co.edu.uptc.order.repo.OrderRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    OrderRepo orderRepo;
    SequenceGenerator sequenceGenerator;
    RestTemplate restTemplate;

    public OrderDTO saveOrderInDB(OrderDTOFromFE orderDetails){
        Long newOrderId = sequenceGenerator.generateNextOrderId();
        UserDTO userDTO = fetchUserDetailsFromUserId(orderDetails.getUserId());
        Order orderToBeSaved =
                new Order(newOrderId, orderDetails.getFoodItemList(), orderDetails.getRestaurant(), userDTO);
        orderRepo.save(orderToBeSaved);
        return OrderMapper.INSTANCE.mapOrderToOrderDTO(orderToBeSaved);
    }

    private UserDTO fetchUserDetailsFromUserId(Long userId){
        return restTemplate.getForObject("http://USER-SERVICE/user/fetchUserById" + userId, UserDTO.class);
    }

}
