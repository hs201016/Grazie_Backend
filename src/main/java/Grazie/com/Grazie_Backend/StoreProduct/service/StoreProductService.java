package Grazie.com.Grazie_Backend.StoreProduct.service;

import Grazie.com.Grazie_Backend.Product.entity.Product;
import Grazie.com.Grazie_Backend.Product.repository.ProductRepository;
import Grazie.com.Grazie_Backend.Store.entity.Store;
import Grazie.com.Grazie_Backend.Store.repository.StoreRepository;
import Grazie.com.Grazie_Backend.StoreProduct.dto.StoreProductDTO;
import Grazie.com.Grazie_Backend.StoreProduct.dto.StoreProductResponseDTO;
import Grazie.com.Grazie_Backend.StoreProduct.entity.StoreProduct;
import Grazie.com.Grazie_Backend.StoreProduct.repository.StoreProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Boolean deleteStoreProduct(Long storeId, Long productId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException("매장을 찾을 수 없습니다."));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));

        storeProductRepository.deleteByStoreAndProduct(store, product);
        return true;
    }

    public StoreProductResponseDTO getProductByStoreId(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException("매장을 찾을 수 없습니다."));

        List<StoreProduct> list = storeProductRepository.findAllByStore(store);
        for (StoreProduct sp : list) {
            sp.setStore(null);
        }

        return StoreProductResponseDTO
                .builder()
                .store(store)
                .storeProducts(list)
                .build();
    }
}
