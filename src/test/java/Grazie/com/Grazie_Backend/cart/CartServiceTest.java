package Grazie.com.Grazie_Backend.cart;

import Grazie.com.Grazie_Backend.Product.Product;
import Grazie.com.Grazie_Backend.Product.ProductRepository;
import Grazie.com.Grazie_Backend.member.entity.User;
import Grazie.com.Grazie_Backend.member.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
                cartItem.getProduct_price()
        );

        assertEquals(1, responseItems.size()); // 장바구니에 1개 상품이 있으니
        System.out.println("Expected Response: " + expectedResponse);
        System.out.println("Actual Response: " + responseItems.get(0));
    }

    @Test
    @DisplayName("특정 상품 삭제하기(정상적으로 삭제할때)")
    @Transactional
    void testDeleteCartItem_QuantityMoreThanRequested() {

        User user = new User();
        Cart cart = new Cart();
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(5);

        cart.getCartItems().add(cartItem);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(any(User.class))).thenReturn(Optional.of(cart));
        when(cartItemRepository.findById(anyLong())).thenReturn(Optional.of(cartItem));

        CartDeleteDTO cartDeleteDTO = new CartDeleteDTO();
        cartDeleteDTO.setUserId(1L);
        cartDeleteDTO.setItemId(1L);
        cartDeleteDTO.setQuantity(2);


        cartService.deleteCartItem(cartDeleteDTO);

        // 검증
        assertEquals(3, cartItem.getQuantity());
        verify(cartItemRepository, times(1)).save(cartItem);
        verify(cartItemRepository, never()).delete(cartItem);
    }


    @Test
    @DisplayName("장바구니 비어 있을 때 삭제 요청할 때 ")
    void testDeleteCartItem_ItemNotInCart() {

        User user = new User();
        Cart cart = new Cart();
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(5);

        // 장바구니 비어 있음
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(any(User.class))).thenReturn(Optional.of(cart));
        when(cartItemRepository.findById(anyLong())).thenReturn(Optional.of(cartItem));

        CartDeleteDTO cartDeleteDTO = new CartDeleteDTO();
        cartDeleteDTO.setUserId(1L);
        cartDeleteDTO.setItemId(1L);
        cartDeleteDTO.setQuantity(2);

        cartService.deleteCartItem(cartDeleteDTO);

        // 검증
        assertTrue(cart.getCartItems().isEmpty());
        verify(cartItemRepository, never()).save(any(CartItem.class));
        verify(cartItemRepository, never()).delete(any(CartItem.class));
    }

    @Test
    @DisplayName("장바구니 지워서 수량이 0이 될떄")
    @Transactional
    void testDeleteCartItem_QuantityEqualToRequested() {

        User user = new User();
        Cart cart = new Cart();
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(2);

        cart.getCartItems().add(cartItem);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(any(User.class))).thenReturn(Optional.of(cart));
        when(cartItemRepository.findById(anyLong())).thenReturn(Optional.of(cartItem));

        CartDeleteDTO cartDeleteDTO = new CartDeleteDTO();
        cartDeleteDTO.setUserId(1L);
        cartDeleteDTO.setItemId(1L);
        cartDeleteDTO.setQuantity(2);


        cartService.deleteCartItem(cartDeleteDTO);

        // 검증
        assertFalse(cart.getCartItems().contains(cartItem));
        verify(cartItemRepository, times(1)).delete(cartItem);
        verify(cartItemRepository, never()).save(cartItem);
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


