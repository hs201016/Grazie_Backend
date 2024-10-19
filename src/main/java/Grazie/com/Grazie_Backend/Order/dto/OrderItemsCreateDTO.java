package Grazie.com.Grazie_Backend.Order.dto;

import Grazie.com.Grazie_Backend.personaloptions.enumfile.Concentration;
import Grazie.com.Grazie_Backend.personaloptions.enumfile.IceAddition;
import Grazie.com.Grazie_Backend.personaloptions.enumfile.TumblerUsage;
import lombok.*;
/*
    Chaean00
    주문 상품 DTO
 */


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemsCreateDTO {
    private Long productId; // 상품 ID
    private int productPrice; // 상품 가격
    private int quantity; // 상품 수량
    private String size; // 상품 사이즈: small, medium, large
    private String temperature; // 상품온도 : ice, hot
    private Long couponId; // 쿠폰 사용 유무: 해당 상품에 쿠폰을 적용한다면 ID, 아니라면 null
    // personal option
    private Concentration concentration; // 농도: "연하게", "보통", "진하게";
    private int shotAddition; // 샷 추가: 몇번?
    private TumblerUsage personalTumbler; // 텀블러 사용유무: "사용함", "사용 안함"
    private int pearlAddition; // 펄 추가: 몇번?
    private int syrupAddition; // 시럽 추가: 몇번?
    private int sugarAddition; // 당 추가: 몇번?
    private int whippedCreamAddition; // 휘핑크림 추가: 몇번?
    private IceAddition iceAddition; // 얼음양: "없음", "적게", "보통", "많이";
}
