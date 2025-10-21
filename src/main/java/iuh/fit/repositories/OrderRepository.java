package iuh.fit.repositories;

import iuh.fit.model.Order;
import iuh.fit.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {



    @Query("SELECT o FROM Order o")
    List<Order> getAllOrders();

    @Query("SELECT ol FROM OrderLine ol LEFT JOIN FETCH ol.product WHERE ol.order.id = :orderId")
    List<OrderLine> getOrderLinesByOrderId(@Param("orderId") Integer orderId);

    @Query("SELECT o FROM Order o WHERE o.id = :id")
    Order getOrderById(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Order o WHERE o.id = :id")
    void deleteOrderById(@Param("id") Integer id);

    @Query("SELECT o FROM Order o WHERE o.customer.name = :username ORDER BY o.date DESC")
    List<Order> findOrdersByUsername(@Param("username") String username);
}
