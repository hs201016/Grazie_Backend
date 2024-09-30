package Grazie.com.Grazie_Backend.cart.service;

import Grazie.com.Grazie_Backend.Product.entity.Product;
import Grazie.com.Grazie_Backend.Product.repository.ProductRepository;
import Grazie.com.Grazie_Backend.cart.dto.CartDeleteDTO;
import Grazie.com.Grazie_Backend.cart.dto.CartItemResponseDTO;
import Grazie.com.Grazie_Backend.cart.entity.Cart;
import Grazie.com.Grazie_Backend.cart.entity.CartItem;
import Grazie.com.Grazie_Backend.cart.repository.CartItemRepository;
import Grazie.com.Grazie_Backend.cart.repository.CartRepository;
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


/**
 * 희수 : 지금은 findByUser 로 유저를 찾지만 곧 security 설정 후 jwt 로 찾을 예정
 */

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
                        cartItem.getProduct().getPrice() * cartItem.getQuantity(),
                        cartItem.getProduct().getSize(),
                        cartItem.getProduct().getTemperature()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteCartItem(long userId, CartDeleteDTO cartDeleteDTO) {
        User user = findUser(userId);
        Cart cart = findCartByUser(user).orElseThrow(
                () -> new EntityNotFoundException("해당 사용자의 장바구니가 없습니다."));
        List<CartItem> cartItem = findCartItemByCartId(cartDeleteDTO.getCartId());

        CartItem cartItemToDelete = cartItem.stream()
                .filter(item -> item.getProduct().getProductId().equals(cartDeleteDTO.getProductId()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("해당 카트에 상품이 존재하지 않습니다."));

        // 수량 감소 또는 삭제 처리 로직
        if (cartItemToDelete.getQuantity() > cartDeleteDTO.getQuantity()) {
            cartItemToDelete.setQuantity(cartItemToDelete.getQuantity() - cartDeleteDTO.getQuantity());
            cartItemRepository.save(cartItemToDelete); // 수정된 CartItem 저장
        } else {
            // 수량보다 많이 지울경우
            cart.getCartItems().remove(cartItemToDelete);
            cartItemRepository.delete(cartItemToDelete); // CartItem 삭제
        }
        cartRepository.save(cart);
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

    @Transactional(readOnly = true)
    public boolean isCartEmpty(Long userId) {
        User user = findUser(userId);
        Cart cart = findCartByUser(user).orElseThrow(
                () -> new EntityNotFoundException("해당 사용자의 장바구니가 없습니다."));
        List<CartItem> cartItem = findCartItemByCartId(cart.getId());

        return cartItem.isEmpty();
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
        return cartRepository.findByUser(user);
    }

    private Cart createCartForUser(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("해당 상품을 찾을 수 없습니다."));
    }


    private List<CartItem> findCartItemByCartId(Long cartId) {
        return cartItemRepository.findByCartId(cartId);
    }

}
