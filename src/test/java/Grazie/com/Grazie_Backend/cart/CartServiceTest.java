package Grazie.com.Grazie_Backend.cart;

import Grazie.com.Grazie_Backend.Product.Product;
import Grazie.com.Grazie_Backend.Product.ProductRepository;
import Grazie.com.Grazie_Backend.cart.dto.CartDeleteDTO;
import Grazie.com.Grazie_Backend.cart.dto.CartItemResponseDTO;
import Grazie.com.Grazie_Backend.cart.entity.Cart;
import Grazie.com.Grazie_Backend.cart.entity.CartItem;
import Grazie.com.Grazie_Backend.cart.repository.CartItemRepository;
import Grazie.com.Grazie_Backend.cart.repository.CartRepository;
import Grazie.com.Grazie_Backend.cart.service.CartService;
import Grazie.com.Grazie_Backend.member.entity.User;
import Grazie.com.Grazie_Backend.member.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CartService cartService;

    private User user;
    private Cart cart;
    private Product product;
    private CartItem cartItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);

        cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);
        cart.setCartItems(new ArrayList<>());

        product = new Product();
        product.setProductId(1L);
        product.setPrice(10000);

        cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(2);
        cartItem.setProduct_price(10000);

        cart.getCartItems().add(cartItem);
    }

    @Test
    @DisplayName("장바구니에 상품 추가하기")
    void addProductToCart() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        cartService.addProductToCart(1L, 1L, 2);

        verify(cartItemRepository, times(1)).save(any(CartItem.class));
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    @DisplayName("장바구니 상품 조회하기")
    void readCartItem() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));

        List<CartItemResponseDTO> responseItems = cartService.readCartItem(1L);

        CartItemResponseDTO expectedResponse = new CartItemResponseDTO(
                cartItem.getProduct().getProductId(),
                cartItem.getQuantity(),
                cartItem.getProduct().getPrice() * cartItem.getQuantity(),
                cartItem.getProduct().getSize(),
                cartItem.getProduct().getTemperature());

        assertEquals(1, responseItems.size()); // 장바구니에 1개 상품이 있으니
        System.out.println("Expected Response: " + expectedResponse);
        System.out.println("Actual Response: " + responseItems.get(0));
    }

    @Test
    @Transactional
    @DisplayName("지정 삭제 테스트")
    public void testDeleteCartItem() {
        // 준비
        long userId = 1L;
        Long cartId = 2L;
        Long productId = 3L;
        int quantityToDelete = 1;

        User user = new User();
        Cart cart = new Cart();
        CartItem cartItem = new CartItem();
        cartItem.setProduct(new Product());
        cartItem.getProduct().setProductId(productId);
        cartItem.setQuantity(5);
        cartItem.setProduct_price(10000);

        cart.getCartItems().add(cartItem);

        CartDeleteDTO cartDeleteDTO = new CartDeleteDTO();
        cartDeleteDTO.setCartId(cartId);
        cartDeleteDTO.setProductId(productId);
        cartDeleteDTO.setQuantity(quantityToDelete);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
        Mockito.when(cartItemRepository.findByCartId(cartId)).thenReturn(List.of(cartItem));

        // 삭제
        cartService.deleteCartItem(userId, cartDeleteDTO);

        Mockito.verify(cartItemRepository, Mockito.times(1)).save(cartItem);
        Mockito.verify(cartItemRepository, Mockito.never()).delete(cartItem); // delete 메소드 호출 안됬는지 확인
        Assertions.assertEquals(4, cartItem.getQuantity()); // 수량 4 남았는 지 확인
    }

    @Test
    @Transactional
    @DisplayName("딱맞게 지웠을 떄")
    public void testDeleteCartItem_deletion() {
        // 준비
        long userId = 1L;
        Long cartId = 2L;
        Long productId = 3L;
        int quantityToDelete = 10;

        User user = new User();
        Cart cart = new Cart();
        CartItem cartItem = new CartItem();
        cartItem.setProduct(new Product());
        cartItem.getProduct().setProductId(productId);
        cartItem.setQuantity(5);
        cartItem.setProduct_price(10000);

        cart.getCartItems().add(cartItem);

        CartDeleteDTO cartDeleteDTO = new CartDeleteDTO();
        cartDeleteDTO.setCartId(cartId);
        cartDeleteDTO.setProductId(productId);
        cartDeleteDTO.setQuantity(quantityToDelete);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
        Mockito.when(cartItemRepository.findByCartId(cartId)).thenReturn(List.of(cartItem));

        // 실행
        cartService.deleteCartItem(userId, cartDeleteDTO);

        // 검증
        Mockito.verify(cartItemRepository, Mockito.never()).save(cartItem);
        Mockito.verify(cartItemRepository, Mockito.times(1)).delete(cartItem); // delete 실행됨?
        Assertions.assertTrue(cart.getCartItems().isEmpty()); // 비워져 있음?

}

    @Test
    @DisplayName("사용자 못 찾았을 때")
    void findUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            cartService.readCartItem(1L);
        });

        assertEquals("사용자를 찾을 수 없습니다.", exception.getMessage());
    }





    @Test
    @DisplayName("장바구니 못 찾았을 때")
    void findCartNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(user)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            cartService.readCartItem(1L);
        });

        assertEquals("해당 사용자의 장바구니가 없습니다.", exception.getMessage());
    }
}


