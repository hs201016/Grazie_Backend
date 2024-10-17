package Grazie.com.Grazie_Backend.cart.service;

import Grazie.com.Grazie_Backend.Config.SecurityUtils;
import Grazie.com.Grazie_Backend.Config.UserAdapter;
import Grazie.com.Grazie_Backend.Product.entity.Product;
import Grazie.com.Grazie_Backend.Product.repository.ProductRepository;
import Grazie.com.Grazie_Backend.Product.service.ProductCalcService;
import Grazie.com.Grazie_Backend.cart.dto.*;
import Grazie.com.Grazie_Backend.cart.entity.Cart;
import Grazie.com.Grazie_Backend.cart.entity.CartItem;
import Grazie.com.Grazie_Backend.cart.repository.CartItemRepository;
import Grazie.com.Grazie_Backend.cart.repository.CartRepository;
import Grazie.com.Grazie_Backend.member.entity.User;
import Grazie.com.Grazie_Backend.member.repository.UserRepository;
import Grazie.com.Grazie_Backend.personaloptions.PersonalOptionRepository;
import Grazie.com.Grazie_Backend.personaloptions.entity.PersonalOptions;
import Grazie.com.Grazie_Backend.personaloptions.service.PersonalOptionService;
import Grazie.com.Grazie_Backend.personaloptions.service.PersonalOptionPricingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
    private final PersonalOptionPricingService personalOptionPricingService;
    private final PersonalOptionService personalOptionService;
    private final ProductCalcService productCalcService;
    private final PersonalOptionRepository personalOptionRepository;

    @Transactional
    public Long addProductToCart(CartItemRequest cartRequest) {
        UserAdapter currentUser = SecurityUtils.getCurrentUser();
        User user = findUser(currentUser.getUser().getId());
        Cart cart = findCartByUser(user).orElseGet(() -> createCartForUser(user));

        Product product = findProductById(cartRequest.getProductId());

        PersonalOptions options = personalOptionService.createPersonalOptions(cartRequest);
        //영속화를 위해 추가옵션 먼저 저장
        PersonalOptions savedOptions = personalOptionRepository.save(options);

        // 이미 장바구니에 있는지 확인, 옵션도 고려
        CartItem existingCartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(cartRequest.getProductId()) &&
                        item.getPersonalOptions().equals(options)) // 퍼스널 옵션 비교
                .findFirst()
                .orElse(null);

        if (existingCartItem != null) {
            // 기존 아이템이 있으면 수량만 업데이트 하도록
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartRequest.getQuantity());
            existingCartItem.setPrice(0); // 가격은 일단 0으로 설정
            cartItemRepository.save(existingCartItem);
        } else {
            // 없을 경우에는 새로 추가
            CartItem cartItem = createCartItem(cart, product, cartRequest.getQuantity(), savedOptions);
            cartItem.setSize(cartRequest.getSize()); // 사이즈 설정
            cartItem.setTemperature(cartRequest.getTemperature()); // 온도 설정
            cartItem.setPrice(0); // 가격은 0이라고 설정
            cart.getCartItems().add(cartItem);
            cartItemRepository.save(cartItem);
        }
        return cart.getId(); // 장바구니의 PK값 반환
    }



    @Transactional
    public List<CartItemResponse> readCart() {
        UserAdapter currentUser = SecurityUtils.getCurrentUser();
        User user = currentUser.getUser();
        Cart cart = findCartByUser(user).orElseGet(() -> createCartForUser(user)); // cart 가 없으면 사용자에게 생성

        List<CartItem> cartItems = cart.getCartItems();
        System.out.println(cartItems);
        // 카트 아이템이 없을 경우 빈 리스트 반환
        if (cartItems == null || cartItems.isEmpty()) {
            return new ArrayList<>();
        }


        List<CartItemResponse> responseList = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
             int totalPrice = calculateTotalPrice(cartItem.getId(), cartItem.getProduct().getProductId());

            // CartItemResponse 객체 생성
            CartItemResponse response = new CartItemResponse();
            response.setProductName(cartItem.getProduct().getName());
            response.setSize(cartItem.getSize());
            response.setTemperature(cartItem.getTemperature());
            response.setPrice(totalPrice);
            response.setQuantity(cartItem.getQuantity());
            response.setPersonalOptions(cartItem.getPersonalOptions());

            responseList.add(response);
        }
        return responseList;
    }

    public int calculateTotalPrice(Long cartItemId, Long productId) {

        CartItem cartItem = findCartItemByCartItemId(cartItemId);

        if (cartItem == null) {
            throw new IllegalArgumentException("CartItem not found for ID: " + cartItemId);
        }


        String size = cartItem.getSize();
        String temperature = cartItem.getTemperature();
        PersonalOptions personalOptions = cartItem.getPersonalOptions();
        int quantity = cartItem.getQuantity();

        int productPrice = productCalcService.calculateTotalPrice(size, temperature, productId);
        int additionalPrice = personalOptionPricingService.calculateAdditionalPrice(personalOptions);
        return (productPrice + additionalPrice) * quantity;
    }


    @Transactional
    public void deleteCartItem(CartDeleteRequest cartDeleteRequest) {
        UserAdapter currentUser = SecurityUtils.getCurrentUser();

        // 사용자의 장바구니 찾기
        Cart cart = findCartByUser(currentUser.getUser()).orElseThrow(
                () -> new EntityNotFoundException("해당 사용자의 장바구니가 없습니다."));

        // 장바구니 아이템 목록에서 삭제할 아이템 찾기
        List<CartItem> cartItems = cart.getCartItems();
        CartItem cartItemToDelete = cartItems.stream()
                .filter(item -> item.getId().equals(cartDeleteRequest.getCartItemId()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("해당 카트에 상품이 존재하지 않습니다."));

        // 수량이 2개 이상일 때
        if (cartItemToDelete.getQuantity() > 1) {
            // 수량을 1개 줄이는 경우
            cartItemToDelete.setQuantity(cartItemToDelete.getQuantity() - 1);
            cartItemRepository.save(cartItemToDelete);
        } else {
            // 수량이 1개인 경우 삭제
            cartItems.remove(cartItemToDelete);
            cartItemRepository.delete(cartItemToDelete);
        }
        cartRepository.save(cart);
    }

    @Transactional
    public void increaseCartItemQuantity(CartIncreaseRequest cartIncreaseRequest) {
        UserAdapter currentUser = SecurityUtils.getCurrentUser();

        // 사용자의 장바구니 찾기
        Cart cart = findCartByUser(currentUser.getUser()).orElseThrow(
                () -> new EntityNotFoundException("해당 사용자의 장바구니가 없습니다."));

        // 장바구니 아이템 목록에서 증가시킬 아이템 찾기
        List<CartItem> cartItems = cart.getCartItems();
        CartItem cartItemToUpdate = cartItems.stream()
                .filter(item -> item.getId().equals(cartIncreaseRequest.getCartItemId()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("해당 카트에 상품이 존재하지 않습니다."));

        // 수량 증가 처리
        cartItemToUpdate.setQuantity(cartItemToUpdate.getQuantity() + 1);
        cartItemRepository.save(cartItemToUpdate);

        cartRepository.save(cart);
    }
    @Transactional
    public void deleteAllCartItems() {
        UserAdapter currentUser = SecurityUtils.getCurrentUser();
        User user = findUser(currentUser.getUser().getId());
        Cart cart = findCartByUser(user).orElseGet(() -> createCartForUser(user)); // cart 가 없으면 사용자에게 생성

        List<CartItem> cartItems = new ArrayList<>(cart.getCartItems());
        cart.getCartItems().clear();

        for (CartItem cartItem : cartItems) {
            cartItemRepository.delete(cartItem);
        }
        cartRepository.save(cart);
    }

//    @Transactional(readOnly = true)
//    public boolean isCartEmpty(Long userId) {
//        User user = findUser(userId);
//        Cart cart = findCartByUser(user).orElseThrow(
//                () -> new EntityNotFoundException("해당 사용자의 장바구니가 없습니다."));
//        List<CartItem> cartItem = findCartItemByCartId(cart.getId());
//
//        return cartItem.isEmpty();
//    }


    private CartItem createCartItem(Cart cart, Product product, int quantity, PersonalOptions options) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setPersonalOptions(options); // 퍼스널 옵션 설정
        return cartItem;
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
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


    public CartItem findCartItemByCartItemId(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("CartItem not found for ID: " + cartItemId));
    }


    private CartItem findCartItemByCartId(Long cartId) {
        return cartItemRepository.findByCartId(cartId);
    }

}
