package Grazie.com.Grazie_Backend.Order.dto;

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
    private int product_price;
    private int quantity;
    private String size;
    private String temperature;
    // personal option
    private int shotAddition;
    private int personalTumbler;
    private int pearlAddition;
    private int syrupAddition;
    private int whippedCreamAddition;
    private int iceAddition;
}
