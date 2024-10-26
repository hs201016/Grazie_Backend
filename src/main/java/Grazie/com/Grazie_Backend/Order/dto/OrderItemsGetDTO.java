package Grazie.com.Grazie_Backend.Order.dto;

import Grazie.com.Grazie_Backend.Product.entity.Product;
import Grazie.com.Grazie_Backend.coupon.Coupon;
import Grazie.com.Grazie_Backend.personaloptions.entity.PersonalOptions;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderItemsGetDTO {
    private Long order_item_id;

    private int product_price;
    private int quantity;
    private int total_price;
    private String size;
    private String temperature;
    private Product product;
    private PersonalOptions personalOptions;
    private Coupon coupon;
}
