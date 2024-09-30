package Grazie.com.Grazie_Backend.Order.OrderItems.entity;

import Grazie.com.Grazie_Backend.Order.entity.Order;
import Grazie.com.Grazie_Backend.Product.entity.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/*
    Chaean00
    주문 상품 Entity
 */
@Entity
@Table(name = "order_items")
@Getter
@Setter
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_item_id;

    @Column(name = "product_price", nullable = false)
    @Min(0)
    @NotNull
    private int product_price;

    @Column(name = "quantity", nullable = false)
    @Min(0)
    @NotNull
    private int quantity;

    @Column(name = "total_price", nullable = false)
    @Min(0)
    @NotNull
    private int total_price;

    @Column(name = "size", nullable = false)
    @Size(max = 8)
    @NotNull
    private String size;

    @Column(name = "temperature", nullable = false)
    @Size(max = 5)
    @NotNull
    private String temperature;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @NotNull
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull
    private Product product;
    @Override
    public String toString() {
        return "OrderItems{" +
                "order_item_id=" + order_item_id +
                ", product=" + product +
                ", quantity=" + quantity +
                ", product_price=" + product_price +
                ", total_price=" + total_price +
                '}';
    }
}
