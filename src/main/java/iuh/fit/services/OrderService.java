package iuh.fit.services;

import iuh.fit.model.Order;
import iuh.fit.model.Customer;
import iuh.fit.model.OrderLine;
import iuh.fit.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public Order getOrderById(Integer id) {
        return orderRepository.getOrderById(id);
    }

    public List<OrderLine> getOrderDetailById(Integer id){
        return orderRepository.getOrderLinesByOrderId(id);
    }

    @Transactional
    public void deleteOrder(Integer id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            orderRepository.delete(order);
        }
    }
}
