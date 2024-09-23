package Grazie.com.Grazie_Backend.Pay.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayResponseDTO {
    private Long id;
    private String imp_uid;
    private int amount;
    private int cancelAmount;
    private String currency;
    private String status;
    private Timestamp paidAt;
    private String buyerName;
    private String buyerEmail;
    private String payMethod;
    private String cardName;
    private String pgProvider;
}
