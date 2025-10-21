package iuh.fit.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Calendar;

@Entity
@Table(name = "customers")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Temporal(TemporalType.DATE)
    private Calendar customerSince;
}
