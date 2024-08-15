package Grazie.com.Grazie_Backend.Order.DTO;

import Grazie.com.Grazie_Backend.Store.Store;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderGetDTO {
    private Long order_id;
    private Long id;
    private Store store;
    private Long coupon_id;
    private int total_price;
    private int discount_price;
    private int final_price;
    private LocalDateTime order_date;
    private String order_mode;
    private String accept;
    private String requirement;
}



