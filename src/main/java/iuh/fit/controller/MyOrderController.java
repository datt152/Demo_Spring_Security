package iuh.fit.controller;

import iuh.fit.model.Order;
import iuh.fit.model.OrderLine;
import iuh.fit.repositories.OrderRepository;
import iuh.fit.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/my")
public class MyOrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/orders")
    public String listMyOrders(Authentication authentication, Model model) {
        String username = authentication.getName();
        List<Order> orders = orderRepository.findOrdersByUsername(username);
        model.addAttribute("orders", orders);
        return "my-orders";
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/orders/{id}")
    public String viewMyOrder(@PathVariable Integer id, 
                              Authentication authentication, 
                              Model model) {
        String username = authentication.getName();
        Order order = orderService.getOrderById(id);
        
        // Check if order belongs to the logged-in user
        if (order == null || !order.getCustomer().getName().equals(username)) {
            return "redirect:/my/orders";
        }

        List<OrderLine> orderLines = orderService.getOrderDetailById(id);
        model.addAttribute("order", order);
        model.addAttribute("orderLines", orderLines);
        return "my-order-details";
    }
}
