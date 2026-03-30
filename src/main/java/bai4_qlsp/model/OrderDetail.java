package bai4_qlsp.model;

import lombok.*;
import jakarta.persistence.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private int productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private long price;

    @Column(nullable = false)
    private int quantity;

    public long getTotalPrice() {
        return price * quantity;
    }
}
