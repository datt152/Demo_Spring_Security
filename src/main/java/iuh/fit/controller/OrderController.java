package iuh.fit.controller;

import iuh.fit.model.Order;
import iuh.fit.model.OrderLine;
import iuh.fit.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    // Chỉ Customer và Admin mới xem danh sách đơn hàng
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @GetMapping
    public String listOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders"; // -> templates/orders.html
    }

    // Chỉ Customer mới tạo đơn hàng mới
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("order", new Order());
        return "order-form"; // -> templates/order-form.html
    }

    // Chỉ Customer và Admin mới xem chi tiết đơn hàng
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @GetMapping("/{id}")
    public String viewOrder(@PathVariable Integer id, Model model) {
        Order order = orderService.getOrderById(id);
        List<OrderLine> orderLines = orderService.getOrderDetailById(id);
        model.addAttribute("order", order);
        model.addAttribute("orderLines", orderLines);
        return "order-details";
    }

    // Chỉ Admin mới được xóa đơn hàng
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }
}
