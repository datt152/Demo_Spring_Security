package iuh.fit.controller;

import iuh.fit.cart.Cart;
import iuh.fit.cart.CartItem;
import iuh.fit.model.*;
import iuh.fit.repositories.CustomerRepository;
import iuh.fit.repositories.OrderRepository;
import iuh.fit.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private Cart cart;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @PostMapping("/add")
    public String addToCart(@RequestParam Integer productId,
                            @RequestParam(defaultValue = "1") Integer quantity,
                            RedirectAttributes redirectAttributes) {
        Product product = productService.getProductById(productId);
        
        if (product == null) {
            redirectAttributes.addFlashAttribute("error", "Sản phẩm không tồn tại!");
            return "redirect:/products";
        }

        if (product.getStock() == null || product.getStock() < quantity) {
            redirectAttributes.addFlashAttribute("error", "Không đủ hàng trong kho!");
            return "redirect:/products/" + productId;
        }

        cart.addItem(product.getId(), product.getName(), product.getPrice(), quantity);
        redirectAttributes.addFlashAttribute("success", "Đã thêm vào giỏ hàng!");
        return "redirect:/products/" + productId;
    }

    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @GetMapping
    public String viewCart(Model model) {
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @PostMapping("/update")
    public String updateCart(@RequestParam Integer productId,
                             @RequestParam Integer quantity,
                             RedirectAttributes redirectAttributes) {
        if (quantity <= 0) {
            redirectAttributes.addFlashAttribute("error", "Số lượng phải lớn hơn 0!");
            return "redirect:/cart";
        }

        Product product = productService.getProductById(productId);
        if (product.getStock() == null || product.getStock() < quantity) {
            redirectAttributes.addFlashAttribute("error", "Không đủ hàng trong kho!");
            return "redirect:/cart";
        }

        cart.updateQuantity(productId, quantity);
        redirectAttributes.addFlashAttribute("success", "Đã cập nhật số lượng!");
        return "redirect:/cart";
    }

    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Integer productId,
                                 RedirectAttributes redirectAttributes) {
        cart.removeItem(productId);
        redirectAttributes.addFlashAttribute("success", "Đã xóa khỏi giỏ hàng!");
        return "redirect:/cart";
    }

    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @PostMapping("/checkout")
    public String checkout(Authentication authentication, RedirectAttributes redirectAttributes) {
        if (cart.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Giỏ hàng trống!");
            return "redirect:/cart";
        }

        String username = authentication.getName();
        Customer customer = customerRepository.findByUsername(username);
        
        if (customer == null) {
            // Create a new customer if not found
            customer = new Customer();
            customer.setName(username);
            customer.setCustomerSince(Calendar.getInstance());
            customer = customerRepository.save(customer);
        }

        // Validate stock for all items
        for (CartItem item : cart.getItems()) {
            Product product = productService.getProductById(item.getProductId());
            if (product.getStock() == null || product.getStock() < item.getQuantity()) {
                redirectAttributes.addFlashAttribute("error", 
                    "Sản phẩm " + item.getProductName() + " không đủ hàng!");
                return "redirect:/cart";
            }
        }

        // Create order
        Order order = new Order();
        order.setCustomer(customer);
        order.setDate(Calendar.getInstance());

        Set<OrderLine> orderLines = new HashSet<>();
        for (CartItem item : cart.getItems()) {
            Product product = productService.getProductById(item.getProductId());
            
            OrderLine orderLine = new OrderLine();
            orderLine.setProduct(product);
            orderLine.setAmount(item.getQuantity());
            orderLine.setUnitPrice(item.getUnitPrice());
            orderLine.setPurchasePrice(item.getUnitPrice()); // Keep backward compatibility
            orderLine.setOrder(order);
            orderLines.add(orderLine);

            // Decrement stock
            product.setStock(product.getStock() - item.getQuantity());
            productService.updateProduct(product);
        }

        order.setOrderLines(orderLines);
        orderRepository.save(order);

        cart.clear();
        redirectAttributes.addFlashAttribute("success", "Đặt hàng thành công! Mã đơn hàng: " + order.getId());
        return "redirect:/my/orders";
    }
}
