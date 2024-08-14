package Grazie.com.Grazie_Backend.Order;

import Grazie.com.Grazie_Backend.Order.OrderItems.OrderItemsDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderCreateRequest {
    private OrderDTO orderDTO;
    private List<OrderItemsDTO> orderItemsDTOs;
}
