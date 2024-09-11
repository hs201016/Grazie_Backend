package Grazie.com.Grazie_Backend.cart.controller;

import Grazie.com.Grazie_Backend.cart.dto.CartDTO;
import Grazie.com.Grazie_Backend.cart.dto.CartDeleteDTO;
import Grazie.com.Grazie_Backend.cart.dto.CartItemResponseDTO;
import Grazie.com.Grazie_Backend.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private final CartService cartService;


    @PostMapping("/add")
    public ResponseEntity<String> addProductCart(@RequestBody CartDTO cartDTO) {
        cartService.addProductToCart(cartDTO.getUserId(), cartDTO.getProductId(), cartDTO.getQuantity());
        return ResponseEntity.ok("성공적으로 저장되었습니다!");
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItemResponseDTO>> readCartItem(@RequestParam("userId") Long userId) {
        List<CartItemResponseDTO> cartItems = cartService.readCartItem(userId);
        return ResponseEntity.ok(cartItems);
    }

    @DeleteMapping("/deleteProduct")
    public ResponseEntity<?> deleteProduct(@RequestParam("id") long userId, @RequestBody CartDeleteDTO cartDeleteDTO) {
        cartService.deleteCartItem(userId, cartDeleteDTO);
        return ResponseEntity.ok("성공적으로 삭제되었습니다.");
    }

    @DeleteMapping("deleteAll")
    public ResponseEntity<?> deleteAllProduct(@RequestParam("userId") Long userId) {
        cartService.deleteAllCartItems(userId);
        return ResponseEntity.ok("성공적으로 삭제되었습니다.");
    }
}
