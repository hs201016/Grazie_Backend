package Grazie.com.Grazie_Backend.Order.DTO;

import Grazie.com.Grazie_Backend.Store.Store;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/*
    Chaean00
    주문 응답 DTO
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderGetResponseDTO {
    private OrderGetDTO orderGetDTO;
    private List<OrderItemsGetDTO> orderItemsGetDTOs;
}
