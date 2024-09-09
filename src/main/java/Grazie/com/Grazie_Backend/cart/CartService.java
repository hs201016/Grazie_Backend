package Grazie.com.Grazie_Backend.cart;

import Grazie.com.Grazie_Backend.Product.Product;
import Grazie.com.Grazie_Backend.Product.ProductRepository;
import Grazie.com.Grazie_Backend.member.entity.User;
import Grazie.com.Grazie_Backend.member.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addProductToCart(Long userId, Long productId, int quantity) {
        User user = findUser(userId);
        Cart cart = findCartByUser(user).orElseGet(() -> createCartForUser(user));


        Product product = findProductById(productId);

        CartItem cartItem = createCartItem(cart, product, quantity);

        cart.getCartItems().add(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Transactional(readOnly = true)
    public List<CartItemResponseDTO> readCartItem(Long userId) {
        User user = findUser(userId);
        Cart cart = findCartByUser(user).orElseThrow(
                () -> new EntityNotFoundException("해당 사용자의 장바구니가 없습니다."));

        return cart.getCartItems().stream()
                .map(cartItem -> new CartItemResponseDTO(
                        cartItem.getProduct().getProductId(),
                        cartItem.getQuantity(),
                        cartItem.getProduct().getPrice()))
                .collect(Collectors.toList());

    }

    @Transactional
    public void deleteCartItem(CartDeleteDTO cartDeleteDTO) {
        User user = findUser(cartDeleteDTO.getUserId());
        Cart cart = findCartByUser(user).orElseThrow(
                () -> new EntityNotFoundException("해당 사용자의 장바구니가 없습니다."));
        CartItem cartItem = findCartItemByItem(cartDeleteDTO.getItemId());

        if (cart.getCartItems().contains(cartItem)) {
            // 수량 더 많으면 지우고 저장
            if (cartItem.getQuantity() > cartDeleteDTO.getQuantity()) {
                cartItem.setQuantity(cartItem.getQuantity() - cartDeleteDTO.getQuantity());
                cartItemRepository.save(cartItem);
            } else {
                // 수량이 0이 되면 아예 장바구니에서 삭제
                cart.getCartItems().remove(cartItem);
                cartItemRepository.delete(cartItem);
            }
        }
    }


    @Transactional
    public void deleteAllCartItems(Long userId) {
        User user = findUser(userId);
        Cart cart = findCartByUser(user).orElseGet(() -> createCartForUser(user)); // cart 가 없으면 사용자에게 생성

        List<CartItem> cartItems = new ArrayList<>(cart.getCartItems());
        cart.getCartItems().clear();

        for (CartItem cartItem : cartItems) {
            cartItemRepository.delete(cartItem);
        }
        cartRepository.save(cart);
    }


    private CartItem createCartItem(Cart cart, Product product, int quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setProduct_price(product.getPrice() * quantity);
        return cartItem;
    }

    private User findUser(Long userId) {
        return  userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
    }

    private Optional<Cart> findCartByUser(User user) {
        return cartRepository.findByUser(user); // Else 문 삭제
    }

    private Cart createCartForUser(User user) { // 사용자에게 장바구니 생성
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    private Product findProductById(Long productId) {
        return  productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("해당 상품을 찾을 수 없습니다."));
    }

    private CartItem findCartItemByItem(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("장바구니 항목을 찾을 수 없습니다."));
    }

}
