package Grazie.com.Grazie_Backend.Order.OrderItems;

import lombok.*;
/*
    Chaean00
    주문 상품 DTO
 */


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemsDTO {
    private Long product_id;
    private int quantity;
    private int product_price;
}
