package Grazie.com.Grazie_Backend.cart.controller;

import Grazie.com.Grazie_Backend.Config.SecurityUtils;
import Grazie.com.Grazie_Backend.cart.dto.*;
import Grazie.com.Grazie_Backend.cart.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("/items")
    @Operation(summary = "유저의 장바구니 목록을 조회합니다.", description = "유저의 장바구니 목록을 조회합니다.")
    public ResponseEntity<List<CartItemResponse>> readCartItem() {
        SecurityUtils.getCurrentUser();
        List<CartItemResponse> cartItems = cartService.readCart();
        return ResponseEntity.ok(cartItems);
    }

    @PostMapping("/add")
    @Operation(summary = "유저의 장바구니에 상품을 추가합니다.", description = "유저의 장바구니에 새로운 상품을 추가합니다.")
    public ResponseEntity<String> addProductCart(@RequestBody CartItemRequest cartRequest) {
        SecurityUtils.getCurrentUser();
        cartService.addProductToCart(cartRequest);
        return ResponseEntity.ok("성공적으로 저장되었습니다!");
    }


    @PatchMapping("/decreaseQuantity")
    @Operation(summary = "유저의 장바구니에서 상품을 삭제합니다.", description = "유저의 장바구니에서 특정 상품을 삭제합니다.")
    public ResponseEntity<?> decreaseProduct(@RequestBody CartDeleteRequest cartDeleteRequest) {
        SecurityUtils.getCurrentUser();
        int result = cartService.decreaseCartItemQuantity(cartDeleteRequest);
        return ResponseEntity.ok().body(result);
    }

    @PatchMapping("/increaseQuantity")
    @Operation(summary = "유저의 장바구니에서 상품 수량을 증가시킵니다.", description = "유저의 장바구니에서 특정 상품의 수량을 1 증가시킵니다.")
    public ResponseEntity<?> increaseProduct(@RequestBody CartIncreaseRequest cartIncreaseRequest) {
        SecurityUtils.getCurrentUser();
        int result = cartService.increaseCartItemQuantity(cartIncreaseRequest);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCartItem(@RequestBody CartDeleteRequest cartDeleteRequest) {
        SecurityUtils.getCurrentUser();
        cartService.deleteCartItem(cartDeleteRequest);
        return ResponseEntity.ok().build();
}

    @DeleteMapping("/deleteAll")
    @Operation(summary = "유저의 장바구니에서 모든 상품을 삭제합니다.", description = "유저의 장바구니에서 모든 상품을 삭제합니다.")
    public ResponseEntity<?> deleteAllProduct() {
        SecurityUtils.getCurrentUser();
        cartService.deleteAllCartItems();
        return ResponseEntity.ok("성공적으로 삭제되었습니다.");
    }



}
