package Grazie.com.Grazie_Backend.Order.dto;

import lombok.*;

import java.util.List;

/*
    Chaean00
    주문 요청 DTO
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequestDTO {
    private OrderCreateDTO orderCreateDTO;
    private List<OrderItemsCreateDTO> orderItemsCreateDTOS;
}
