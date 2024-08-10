package Grazie.com.Grazie_Backend.StoreProduct;

import Grazie.com.Grazie_Backend.Product.Product;
import Grazie.com.Grazie_Backend.Product.ProductRepository;
import Grazie.com.Grazie_Backend.Store.Store;
import Grazie.com.Grazie_Backend.Store.StoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
/*
    Chaean
    매장 상품 Service
 */
public class StoreProductService {

    private final StoreProductRepository storeProductRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;

    @Autowired
    public StoreProductService(StoreProductRepository storeProductRepository, StoreRepository storeRepository, ProductRepository productRepository) {
        this.storeProductRepository = storeProductRepository;
        this.storeRepository = storeRepository;
        this.productRepository = productRepository;
    }

    // StoreID를 이용해 해당 매장에서 어떤 상품을 판매하는지 전체 조회
    public List<Product> getAllProductsByStoreId(Long storeId) {

        return storeProductRepository.findAllProductsByStoreId(storeId);
    }

    // StoreId, ProductId를 이용한 상품 등록
    public StoreProduct createStoreProduct(StoreProductDTO storeProductDTO) {
        StoreProduct storeProduct = new StoreProduct();

        Store store = storeRepository.findById(storeProductDTO.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("매장을 찾을 수 없습니다."));

        Product product = productRepository.findById(storeProductDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        // 중복확인 중복이라면 True
        if (storeProductRepository.existsByStoreAndProduct(store, product)) {
            throw new IllegalArgumentException("이미 등록된 상품입니다.");
        }

        storeProduct.setStore(store);
        storeProduct.setProduct(product);
        storeProduct.setState(storeProductDTO.getState());

        return storeProductRepository.save(storeProduct);
    }
}
