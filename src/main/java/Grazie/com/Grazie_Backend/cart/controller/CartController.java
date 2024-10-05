package Grazie.com.Grazie_Backend.cart.controller;

import Grazie.com.Grazie_Backend.Config.UserAdapter;
import Grazie.com.Grazie_Backend.cart.dto.CartDTO;
import Grazie.com.Grazie_Backend.cart.dto.CartDeleteDTO;
import Grazie.com.Grazie_Backend.cart.dto.CartItemResponseDTO;
import Grazie.com.Grazie_Backend.cart.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private final CartService cartService;


    @PostMapping("/add")
    @Operation(summary = "유저의 장바구니에 상품을 추가합니다.", description = "유저의 장바구니에 새로운 상품을 추가합니다.")
    public ResponseEntity<String> addProductCart(@RequestBody CartDTO cartDTO, @AuthenticationPrincipal UserAdapter userAdapter) {
        Long userId = getUserIdFromUserDetails(userAdapter);
        cartService.addProductToCart(userId, cartDTO.getProductId(), cartDTO.getQuantity());
        return ResponseEntity.ok("성공적으로 저장되었습니다!");
    }

    @GetMapping("/items")
    @Operation(summary = "유저의 장바구니 목록을 조회합니다.", description = "유저의 장바구니 목록을 조회합니다.")
    public ResponseEntity<List<CartItemResponseDTO>> readCartItem(@AuthenticationPrincipal UserAdapter userAdapter) {
        Long userId = getUserIdFromUserDetails(userAdapter);
        List<CartItemResponseDTO> cartItems = cartService.readCartItem(userId);
        return ResponseEntity.ok(cartItems);
    }

    @DeleteMapping("/deleteProduct")
    @Operation(summary = "유저의 장바구니에서 상품을 삭제합니다.", description = "유저의 장바구니에서 특정 상품을 삭제합니다.")
    public ResponseEntity<?> deleteProduct(@AuthenticationPrincipal UserAdapter userAdapter, @RequestBody CartDeleteDTO cartDeleteDTO) {
        Long userId = getUserIdFromUserDetails(userAdapter);
        cartService.deleteCartItem(userId, cartDeleteDTO);
        return ResponseEntity.ok("성공적으로 삭제되었습니다.");
    }

    @DeleteMapping("deleteAll")
    @Operation(summary = "유저의 장바구니에서 모든 상품을 삭제합니다.", description = "유저의 장바구니에서 모든 상품을 삭제합니다.")
    public ResponseEntity<?> deleteAllProduct(@AuthenticationPrincipal UserAdapter userAdapter) {
        Long userId = getUserIdFromUserDetails(userAdapter);
        cartService.deleteAllCartItems(userId);
        return ResponseEntity.ok("성공적으로 삭제되었습니다.");
    }

    private Long getUserIdFromUserDetails(@AuthenticationPrincipal UserAdapter userAdapter) {
        return userAdapter.getUser().getId();
    }
}
