package Grazie.com.Grazie_Backend.Order.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class OrderSuccessDTO {
    private int finalPrice;
    private Long orderId;
    private String message;
}
