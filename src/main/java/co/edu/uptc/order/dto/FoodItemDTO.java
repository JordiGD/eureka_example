package co.edu.uptc.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodItemDTO {

    private Long id;
    private String itemName;
    private String itemDescription;
    private boolean isVeg;
    private Number price;
    private Long restaurantId;
    private Integer quantity;
}