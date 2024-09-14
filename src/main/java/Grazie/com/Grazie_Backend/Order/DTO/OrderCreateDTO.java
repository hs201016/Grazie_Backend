package Grazie.com.Grazie_Backend.Order.DTO;

import lombok.*;

import java.time.LocalDateTime;

/*
    Chaean00
    주문 DTO
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderCreateDTO {
    private LocalDateTime order_date;
    private String order_mode;
    private Long store_id;
    private Long user_id;
    private Long coupon_id;
    private String cup_type;
    private String requirement;
}

