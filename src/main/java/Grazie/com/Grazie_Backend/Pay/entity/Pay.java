package Grazie.com.Grazie_Backend.Pay.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "pay")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Pay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String impUid;

    @Column(nullable = false, length = 255)
    private String merchantUid;

    @Column(nullable = false)
    private int amount;

    @Column
    private int cancelAmount;
    @Column
    private Integer cancelledAt;

    @Column(nullable = false, length = 10)
    private String currency;

    @Column(nullable = false, length = 50)
    private String status;

    @Column
    private Integer paidAt;

    @Column
    private Integer failedAt;

    @Column(length = 100)
    private String buyerName;

    @Column(length = 100)
    private String buyerEmail;

    @Column(nullable = false, length = 50)
    private String payMethod;

    @Column(nullable = false, length = 50)
    private String pgProvider;

    @Column(length = 50)
    private String cardName;
}
