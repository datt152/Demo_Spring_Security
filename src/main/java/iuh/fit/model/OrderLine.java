package iuh.fit.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "order_lines")
@Data
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Product product;

    private Integer amount;

    private BigDecimal purchasePrice;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @ManyToOne
    private Order order;
}
