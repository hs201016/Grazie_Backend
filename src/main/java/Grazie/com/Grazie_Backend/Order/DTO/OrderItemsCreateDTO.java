package Grazie.com.Grazie_Backend.Order.DTO;

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
public class OrderItemsCreateDTO {
    private Long product_id;
    private int quantity;
    private String size;
    private String temperature;
    private int product_price;
}
