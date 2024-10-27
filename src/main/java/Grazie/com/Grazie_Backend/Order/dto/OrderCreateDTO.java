package Grazie.com.Grazie_Backend.Order.dto;

import Grazie.com.Grazie_Backend.personaloptions.enumfile.Concentration;
import Grazie.com.Grazie_Backend.personaloptions.enumfile.IceAddition;
import lombok.*;

import java.time.LocalDateTime;

/*
    Chaean00
    주문 DTO
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderCreateDTO {
    private LocalDateTime order_date; // 주문 날짜
    private String order_mode; // 주문 타입: 매장, 배달, 테이크아웃
    private String requirement; // 요구사항
    private Long store_id; // 주문한 매장 ID
//    private Long user_id; // 주문한 사용자의 ID
//    private Long coupon_id;
}