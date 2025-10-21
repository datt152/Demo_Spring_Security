package iuh.fit.repositories;

import iuh.fit.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p")
    List<Product> getAllProducts();

    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Product getProductById(@Param("id") Integer id);

    @Query(value = "INSERT INTO product (name, price, in_stock, stock) VALUES (:name, :price, :inStock, 0)", nativeQuery = true)
    void createProduct(@Param("name") String name,
                       @Param("price") BigDecimal price,
                       @Param("inStock") boolean inStock);
    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.price = :price WHERE p.id = :id")
    void updateProductPrice(@Param("id") Integer id, @Param("price") BigDecimal price);
    
    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.inStock = :inStock WHERE p.id = :id")
    void updateProductInStock(@Param("id") Integer id, @Param("inStock") boolean inStock);

    @Modifying
    @Transactional
    @Query("DELETE FROM Product p WHERE p.id = :id")
    void deleteProductById(@Param("id") Integer id);
    
    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.name = :name WHERE p.id = :id")
    void updateProductName(Integer id, String name);

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.stock = :stock WHERE p.id = :id")
    void updateProductStock(@Param("id") Integer id, @Param("stock") Integer stock);
}
