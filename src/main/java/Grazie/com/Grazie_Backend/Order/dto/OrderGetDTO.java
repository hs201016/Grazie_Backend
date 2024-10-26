package Grazie.com.Grazie_Backend.Order.dto;

import Grazie.com.Grazie_Backend.Pay.entity.Pay;
import Grazie.com.Grazie_Backend.Store.entity.Store;
import Grazie.com.Grazie_Backend.member.entity.User;
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
    private int total_price;
    private int discount_price;
    private int final_price;
    private LocalDateTime order_date;
    private String order_mode;
    private String accept;
    private String requirement;
    private Store store;
    private User user;
    private Pay pay;
}



