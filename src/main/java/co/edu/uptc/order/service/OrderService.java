package co.edu.uptc.order.service;

import co.edu.uptc.order.dto.FoodItemDTO;
import co.edu.uptc.order.dto.OrderDTO;
import co.edu.uptc.order.dto.OrderDTOFromFE;
import co.edu.uptc.order.dto.Restaurant;
import co.edu.uptc.order.dto.UserDTO;
import co.edu.uptc.order.entity.Order;
import co.edu.uptc.order.mapper.OrderMapper;
import co.edu.uptc.order.repo.OrderRepo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    OrderRepo orderRepo;
    
    @Autowired
    SequenceGenerator sequenceGenerator;
    
    @Autowired
    RestTemplate restTemplate;

    public List<OrderDTO> getAllOrders(){
        List<Order> orders = orderRepo.findAll();
        return orders.stream()
                .map(OrderMapper.INSTANCE::mapOrderToOrderDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO saveOrderInDB(OrderDTOFromFE orderDetails){
        Long newOrderId = sequenceGenerator.generateNextOrderId();
        UserDTO userDTO = fetchUserDetailsFromUserId(orderDetails.getUserId());
        Restaurant restaurant = fetchRestaurantDetailsFromRestaurantId(orderDetails.getRestaurantId());
        List<FoodItemDTO> foodItems = fetchFoodItemsFromIds(orderDetails.getFoodItemList());
        Order orderToBeSaved =
                new Order(newOrderId, foodItems, restaurant, userDTO);
        orderRepo.save(orderToBeSaved);
        return OrderMapper.INSTANCE.mapOrderToOrderDTO(orderToBeSaved);
    }

    private UserDTO fetchUserDetailsFromUserId(Long userId){
        ResponseEntity<UserDTO> response = restTemplate.getForEntity("http://user/user/fetchUserById/" + userId, UserDTO.class);
        
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }

    private Restaurant fetchRestaurantDetailsFromRestaurantId(Long restaurantId){
        ResponseEntity<Restaurant> response = restTemplate.getForEntity("http://restaurantlisting/restaurant/fetchById/" + restaurantId, Restaurant.class);
        
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new RuntimeException("Restaurant not found with ID: " + restaurantId);
        }
    }

    private List<FoodItemDTO> fetchFoodItemsFromIds(List<Long> foodItemIds){
        String idsParam = foodItemIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        
        ResponseEntity<FoodItemDTO[]> response = restTemplate.getForEntity(
            "http://foodCatalogue/foodItem/fetchByIds?ids=" + idsParam, 
            FoodItemDTO[].class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return Arrays.asList(response.getBody());
        } else {
            throw new RuntimeException("Could not fetch food items from catalogue service");
        }
    }
}
