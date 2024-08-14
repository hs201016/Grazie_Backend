package Grazie.com.Grazie_Backend.Order;

import Grazie.com.Grazie_Backend.Order.OrderItems.OrderItems;
import Grazie.com.Grazie_Backend.Store.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GeneratorType;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_id;

//    @ManyToOne
//    @JoinColumn(name = "id", nullable = false)
//    privae User user_id;
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

//    @ManyToOne
//    @JoinColumn(name = "coupon_id")
//    private Coupon coupon;
    @Column(name = "coupon_id")
    private Long coupon_id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItems> orderItems;

    @Column(name = "total_price")
    private int total_price;
    @Column(name = "discount_price")
    private int discount_price;
    @Column(name = "final_price")
    private int final_price;
    @Column(name = "order_date")
    private LocalDateTime order_date;
    @Column(name = "order_mode")
    private String order_mode;
    @Column(name = "accept")
    private String accept;
    @Column(name = "requirement")
    private String requirement;
}
