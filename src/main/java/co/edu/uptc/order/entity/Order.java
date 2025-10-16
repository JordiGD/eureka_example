package co.edu.uptc.order.entity;

import co.edu.uptc.order.dto.FoodItemDTO;
import co.edu.uptc.order.dto.Restaurant;
import co.edu.uptc.order.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("order")
public class Order {
    private Long orderId;
    private List<FoodItemDTO> foodItemList;
    private Restaurant restaurant;
    private UserDTO user;
}
