package Grazie.com.Grazie_Backend.Store.dto;

import lombok.*;


/*
    Chaean00
    매장 데이터 DTO
 */
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
    private StoreOperatingHours operatingHours;

    @Override
    public String toString() {
        return "StoreDTO{" +
                "store_id=" + store_id +
                ", name='" + name + '\'' +
                ", tel_num='" + tel_num + '\'' +
                ", state=" + state +
                ", location='" + location + '\'' +
                ", road_way='" + road_way + '\'' +
                ", parking=" + parking +
                ", operatingHours=" + operatingHours +
                '}';
    }
}
