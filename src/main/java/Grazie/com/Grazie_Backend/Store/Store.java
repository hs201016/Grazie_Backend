package Grazie.com.Grazie_Backend.Store;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Column(name = "tel_num", length = 50)
    private String tel_num;
    @Column(name = "state", nullable = false)
    private Boolean state;
    @Column(name = "location", nullable = false, length = 255)
    private String location;
    @Column(name = "road_way", nullable = false, length = 255)
    private String road_way;
    @Column(name = "parking", nullable = false)
    private Boolean parking;
    @Column(name = "operating_hours", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
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
