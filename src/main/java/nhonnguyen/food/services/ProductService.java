package nhonnguyen.food.services;

import nhonnguyen.food.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Base64;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import nhonnguyen.food.entity.Product;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private IProductRepository productRepository;
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();

        // Iterate over the products and add the complete base64 image string
        for (Product product : products) {
            String base64Image = "data:image/jpeg;base64," + product.getImage();
            product.setCompleteImage(base64Image);
        }

        return products;
    }
    public Product getProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }
    public void addProduct(Product product, MultipartFile file){
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            product.setImage(Base64.getEncoder().encodeToString(file.getBytes()));

        }catch (IOException e)
        {
            e.printStackTrace();
        }
        productRepository.save(product);
    }
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
    public void updateProduct(Product product) {
        // No changes required in the service layer
        productRepository.save(product);
    }


}
