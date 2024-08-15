package Grazie.com.Grazie_Backend.Order.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/*
    Chaean00
    주문 요청 DTO
 */
@Getter
@Setter
@Builder
public class OrderCreateRequestDTO {
    private OrderCreateDTO orderCreateDTO;
    private List<OrderItemsCreateDTO> orderItemsCreateDTOS;
}
