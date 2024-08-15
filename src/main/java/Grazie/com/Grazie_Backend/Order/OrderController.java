package Grazie.com.Grazie_Backend.Order;

import Grazie.com.Grazie_Backend.Order.DTO.OrderCreateDTO;
import Grazie.com.Grazie_Backend.Order.DTO.OrderCreateRequestDTO;
import Grazie.com.Grazie_Backend.Order.DTO.OrderGetResponseDTO;
import Grazie.com.Grazie_Backend.Order.DTO.OrderItemsCreateDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody OrderCreateRequestDTO orderCreateRequestDTO) {
        try {
            OrderCreateDTO orderCreateDTO = orderCreateRequestDTO.getOrderCreateDTO();
            List<OrderItemsCreateDTO> orderItemsCreateDTOS = orderCreateRequestDTO.getOrderItemsCreateDTOS();

            Order order = orderService.createOrder(orderCreateDTO, orderItemsCreateDTOS);
            return ResponseEntity.ok(order);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Order());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Order());
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<OrderGetResponseDTO> getOrderById(@PathVariable(value = "id") Long order_id) {
        try {
            OrderGetResponseDTO orderGetResponseDTO = orderService.getOrderById(order_id);
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
            return ResponseEntity.ok(orderService.getAllOrder());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
    }

    @GetMapping("/get/store/{id}")
    public ResponseEntity<List<OrderGetResponseDTO>> getOrderByStore(@PathVariable(value = "id") Long store_id) {
        try {
            return ResponseEntity.ok(orderService.getOrderByStoreId(store_id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteOrderById(@PathVariable(value = "id") Long order_id) {        try {
        return ResponseEntity.ok(orderService.deleteOrderById(order_id));
    } catch (EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    }
    }
}
