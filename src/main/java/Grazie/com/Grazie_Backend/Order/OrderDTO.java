package Grazie.com.Grazie_Backend.Order;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDTO {
    private LocalDateTime order_date;
    private String order_mode;
    private Long store_id;
    private Long user_id;
    private Long coupon_id;
    private String requirement;
}
