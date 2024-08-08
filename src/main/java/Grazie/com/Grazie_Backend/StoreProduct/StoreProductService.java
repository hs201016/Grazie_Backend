package Grazie.com.Grazie_Backend.StoreProduct;

import Grazie.com.Grazie_Backend.Product.Product;
import Grazie.com.Grazie_Backend.Product.ProductRepository;
import Grazie.com.Grazie_Backend.Store.StoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
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

    public List<Product> getAllProductsByStoreId(Long storeId) {

        return storeProductRepository.findAllProductsByStoreId(storeId);
    }
}
