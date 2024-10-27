package Grazie.com.Grazie_Backend.Order.controller;

import Grazie.com.Grazie_Backend.Order.dto.*;
import Grazie.com.Grazie_Backend.Order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @Operation(summary = "주문을 생성합니다.", description = "새로운 주문을 생성합니다. (결제 API 호출 이전에 사용되어야합니다")
    public ResponseEntity<OrderSuccessDTO> createOrder(@RequestBody OrderCreateRequestDTO orderCreateRequestDTO) {
        OrderCreateDTO orderCreateDTO = orderCreateRequestDTO.getOrderCreateDTO();
        List<OrderItemsCreateDTO> orderItemsCreateDTOS = orderCreateRequestDTO.getOrderItemsCreateDTOS();

        OrderSuccessDTO orderSuccessDTO = orderService.createOrder(orderCreateDTO, orderItemsCreateDTOS);
        return ResponseEntity.ok(orderSuccessDTO);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "특정 주문을 조회합니다.", description = "orderId를 통해 특정 주문을 조회합니다.")
    public ResponseEntity<OrderGetResponseDTO> getOrderById(@PathVariable(value = "id") Long order_id) {
        OrderGetResponseDTO orderGetResponseDTO = orderService.getOrderById(order_id);
        return ResponseEntity.ok(orderGetResponseDTO);
    }

    @GetMapping("/get/all")
    @Operation(summary = "모든 주문을 조회합니다.", description = "모든 주문을 조회합니다.")
    public ResponseEntity<List<OrderGetResponseDTO>> getAllOrder() {
        return ResponseEntity.ok(orderService.getAllOrder());
    }

    @GetMapping("/get/store/{id}")
    @Operation(summary = "특정 매장의 모든 주문을 조회합니다.", description = "storeId를 통해 매장의 모든 주문을 조회합니다.")
    public ResponseEntity<List<OrderGetResponseDTO>> getOrderByStoreId(@PathVariable(value = "id") Long store_id) {
        return ResponseEntity.ok(orderService.getOrderByStoreId(store_id));
    }

    @GetMapping("/get/user")
    @Operation(summary = "특정 사용자의 모든 주문을 조회합니다.", description = "userId 통해 사용자의 모든 주문을 조회합니다.")
    public ResponseEntity<List<OrderGetResponseDTO>> getOrderByUserId() {
        return ResponseEntity.ok(orderService.getOrderByUserId());
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "특정 주문을 삭제합니다.", description = "orderId를 통해 특정 주문을 삭제합니다.")
    public ResponseEntity<Boolean> deleteOrderById(@PathVariable(value = "id") Long order_id) {
        return ResponseEntity.ok(orderService.deleteOrderById(order_id));
    }

    @PutMapping("/update/state/{id}")
    @Operation(summary = "특정 주문을 수정합니다.", description = "orderId를 통해 특정 주문을 수정합니다.")
    public ResponseEntity<String> updateOrderAcceptById(@PathVariable(value = "id") Long order_id, @RequestParam(value = "state") String state) {
        return ResponseEntity.ok(orderService.updateOrderAcceptById(order_id, state));
    }
}
