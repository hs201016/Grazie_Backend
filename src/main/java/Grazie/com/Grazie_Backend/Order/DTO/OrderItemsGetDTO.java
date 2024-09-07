package Grazie.com.Grazie_Backend.Order.DTO;

import Grazie.com.Grazie_Backend.Product.Product;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderItemsGetDTO {
    private Long order_item_id;
    private Product product;
    private int quantity;
    private String size;
    private int product_price;
    private int total_price;
}
