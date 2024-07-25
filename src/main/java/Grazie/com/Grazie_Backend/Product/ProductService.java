package Grazie.com.Grazie_Backend.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Value("${file.upload-dir}")
    private String uploadDir;

    public Product saveProduct(ProductDTO productDTO) {
        // 이미지 파일 저장
        Product product = new Product();

        product.setName(productDTO.getName());
        product.setImage(uploadDir + "/" + productDTO.getImage() +".jpg");
        product.setPrice(productDTO.getPrice());
        product.setExplanation(productDTO.getExplanation());
        product.setSize(productDTO.getSize());
        product.setInformation(productDTO.getInformation());
        product.setTemperature(productDTO.getTemperature());

        productRepository.save(product);

        return product;
    }
}
