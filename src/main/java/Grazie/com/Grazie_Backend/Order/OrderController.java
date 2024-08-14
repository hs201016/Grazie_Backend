package Grazie.com.Grazie_Backend.Order;

import Grazie.com.Grazie_Backend.Order.OrderItems.OrderItems;
import Grazie.com.Grazie_Backend.Order.OrderItems.OrderItemsDTO;
import jakarta.persistence.EntityNotFoundException;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private OrderServcie orderServcie;

    @Autowired
    public OrderController(OrderServcie orderServcie) {
        this.orderServcie = orderServcie;
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody OrderCreateRequest orderCreateRequest) {
        try {
            OrderDTO orderDTO = orderCreateRequest.getOrderDTO();
            List<OrderItemsDTO> orderItemsDTOs = orderCreateRequest.getOrderItemsDTOs();

            Order order = orderServcie.createOrder(orderDTO, orderItemsDTOs);
            return ResponseEntity.ok(order);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Order());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Order());
        }
    }
}
