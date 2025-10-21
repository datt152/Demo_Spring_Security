package iuh.fit.controllers;

import iuh.fit.model.Product;
import iuh.fit.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    // Guest, Customer, Admin đều có thể xem danh sách
    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products"; // templates/products.html
    }

    // Chỉ Admin được tạo mới sản phẩm
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-form"; // templates/product-form.html
    }

    // Chỉ Admin được lưu sản phẩm (tạo hoặc cập nhật)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product) {
        if (product.getId() == null) {
            productService.createProduct(product);
        } else {
            productService.updateProduct(product);
        }
        return "redirect:/products";
    }

    // Guest, Customer, Admin đều xem được chi tiết sản phẩm
    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Integer id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product-details"; // templates/product-details.html
    }

    // Chỉ Admin được sửa sản phẩm
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Integer id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product-form";
    }

    // Chỉ Admin được xóa sản phẩm
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}
