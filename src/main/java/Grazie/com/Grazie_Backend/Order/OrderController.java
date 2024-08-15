package Grazie.com.Grazie_Backend.Order;

import Grazie.com.Grazie_Backend.Order.DTO.OrderCreateDTO;
import Grazie.com.Grazie_Backend.Order.DTO.OrderCreateRequestDTO;
import Grazie.com.Grazie_Backend.Order.DTO.OrderGetResponseDTO;
import Grazie.com.Grazie_Backend.Order.DTO.OrderItemsCreateDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
/*
    Chaean00
    주문 Controller
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderServcie orderServcie;

    @Autowired
    public OrderController(OrderServcie orderServcie) {
        this.orderServcie = orderServcie;
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody OrderCreateRequestDTO orderCreateRequestDTO) {
        try {
            OrderCreateDTO orderCreateDTO = orderCreateRequestDTO.getOrderCreateDTO();
            List<OrderItemsCreateDTO> orderItemsCreateDTOS = orderCreateRequestDTO.getOrderItemsCreateDTOS();

            Order order = orderServcie.createOrder(orderCreateDTO, orderItemsCreateDTOS);
            return ResponseEntity.ok(order);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Order());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Order());
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<OrderGetResponseDTO> getOrderById(@PathVariable(value = "id") Long id) {
        try {
            OrderGetResponseDTO orderGetResponseDTO = orderServcie.getOrderById(id);
            return ResponseEntity.ok(orderGetResponseDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new OrderGetResponseDTO());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new OrderGetResponseDTO());
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<OrderGetResponseDTO>> getAllOrder() {
        try {
            return ResponseEntity.ok(orderServcie.getAllOrder());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
    }
}
