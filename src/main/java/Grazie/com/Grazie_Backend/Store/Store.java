package Grazie.com.Grazie_Backend.Store;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.validation.annotation.Validated;

/*
    Chaean00
    매장 데이터 Entity
 */
@Entity
@Table(name = "store")
@Getter
@Setter
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id", nullable = false)
    private Long store_id;

    @Column(name = "name", nullable = false)
    @Size(max = 40)
    @NotNull
    private String name;

    @Column(name = "tel_num", length = 50)
    @Size(max = 20)
    private String tel_num;

    @Column(name = "location", nullable = false)
    @Size(max = 100)
    @NotNull
    private String location;

    @Column(name = "road_way", nullable = false)
    @Size(max = 50)
    @NotNull
    private String road_way;

    @Column(name = "parking", nullable = false)
    @NotNull
    private Boolean parking;

    @Column(name = "state", nullable = false)
    @NotNull
    private Boolean state;

    @Column(name = "operating_hours", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    @NotNull
    private StoreOperatingHours operatingHours;

    @Override
    public String toString() {
        return "Store{" +
                "storeId=" + store_id +
                ", name='" + name + '\'' +
                ", telNum='" + tel_num + '\'' +
                ", state=" + state +
                ", location='" + location + '\'' +
                ", roadWay='" + road_way + '\'' +
                ", parking=" + parking +
                '}';
    }
}
