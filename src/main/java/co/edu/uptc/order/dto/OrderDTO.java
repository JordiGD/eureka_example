package co.edu.uptc.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long orderId;
    private List<FoodItemDTO> foodItemList;
    private Restaurant restaurant;
    private UserDTO user;

}
