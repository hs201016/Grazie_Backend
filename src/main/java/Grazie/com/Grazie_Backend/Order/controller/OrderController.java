package Grazie.com.Grazie_Backend.Order.controller;

import Grazie.com.Grazie_Backend.Order.service.OrderService;
import Grazie.com.Grazie_Backend.Order.dto.OrderCreateDTO;
import Grazie.com.Grazie_Backend.Order.dto.OrderCreateRequestDTO;
import Grazie.com.Grazie_Backend.Order.dto.OrderGetResponseDTO;
import Grazie.com.Grazie_Backend.Order.dto.OrderItemsCreateDTO;
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

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<OrderGetResponseDTO> createOrder(@RequestBody OrderCreateRequestDTO orderCreateRequestDTO) {
        OrderCreateDTO orderCreateDTO = orderCreateRequestDTO.getOrderCreateDTO();
        List<OrderItemsCreateDTO> orderItemsCreateDTOS = orderCreateRequestDTO.getOrderItemsCreateDTOS();

        OrderGetResponseDTO orderGetResponseDTO = orderService.createOrder(orderCreateDTO, orderItemsCreateDTOS);
        return ResponseEntity.ok(orderGetResponseDTO);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<OrderGetResponseDTO> getOrderById(@PathVariable(value = "id") Long order_id) {
        OrderGetResponseDTO orderGetResponseDTO = orderService.getOrderById(order_id);
        return ResponseEntity.ok(orderGetResponseDTO);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<OrderGetResponseDTO>> getAllOrder() {
        return ResponseEntity.ok(orderService.getAllOrder());
    }

    @GetMapping("/get/store/{id}")
    public ResponseEntity<List<OrderGetResponseDTO>> getOrderByStoreId(@PathVariable(value = "id") Long store_id) {
        return ResponseEntity.ok(orderService.getOrderByStoreId(store_id));
    }

    @GetMapping("/get/user/{id}")
    public ResponseEntity<List<OrderGetResponseDTO>> getOrderByUserId(@PathVariable(value = "id") Long user_id) {
        return ResponseEntity.ok(orderService.getOrderByUserId(user_id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteOrderById(@PathVariable(value = "id") Long order_id) {
        return ResponseEntity.ok(orderService.deleteOrderById(order_id));
    }

    @PutMapping("/update/state/{id}")
    public ResponseEntity<String> updateOrderAcceptById(@PathVariable(value = "id") Long order_id, @RequestParam(value = "state") String state) {
        return ResponseEntity.ok(orderService.updateOrderAcceptById(order_id, state));
    }
}
