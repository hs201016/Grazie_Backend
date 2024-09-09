package Grazie.com.Grazie_Backend.cart;

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
    public ResponseEntity<?> deleteProduct(@RequestBody CartDeleteDTO cartDeleteDTO) {
        cartService.deleteCartItem(cartDeleteDTO);
        return ResponseEntity.ok("성공적으로 삭제되었습니다.");
    }

    @DeleteMapping("deleteAll")
    public ResponseEntity<?> deleteAllProduct(@RequestParam("userId") Long userId) {
        cartService.deleteAllCartItems(userId);
        return ResponseEntity.ok("성공적으로 삭제되었습니다.");
    }
}
