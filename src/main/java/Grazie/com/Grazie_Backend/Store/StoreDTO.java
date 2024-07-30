package Grazie.com.Grazie_Backend.Store;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDTO {
    private Long store_id;
    private String name;
    private String tel_num;
    private Boolean state;
    private String location;
    private String road_way;
    private Boolean parking;

    @Override
    public String toString() {
        return "StoreDTO{" +
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
