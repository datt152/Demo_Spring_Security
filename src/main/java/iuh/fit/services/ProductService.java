package iuh.fit.services;

import iuh.fit.model.Product;
import iuh.fit.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public Product getProductById(Integer id) {
        return productRepository.getProductById(id);
    }

    public void createProduct(Product p) {
        productRepository.createProduct(p.getName(), p.getPrice(), p.isInStock());
    }

    public void updateProduct(Product p) {
        productRepository.updateProductName(p.getId(), p.getName());
        productRepository.updateProductPrice(p.getId(), p.getPrice());
        productRepository.updateProductStock(p.getId(), p.isInStock());
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteProductById(id);
    }
}
