package Grazie.com.Grazie_Backend.Pay.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDetails {
    @JsonProperty("imp_uid")
    private String impUid;
    @JsonProperty("merchant_uid")
    private String merchantUid;
    @JsonProperty("amount")
    private int amount;
    @JsonProperty("cancel_amount")
    private int cancelAmount;
    @JsonProperty("cancelled_at")
    private Integer cancelledAt;
    @JsonProperty("failed_at")
    private Integer failedAt;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("status")
    private String status;
    @JsonProperty("paid_at")
    private Integer paidAt;
    @JsonProperty("buyer_name")
    private String buyerName;
    @JsonProperty("buyer_email")
    private String buyerEmail;
    @JsonProperty("pay_method")
    private String payMethod;
    @JsonProperty("card_name")
    private String cardName;
    @JsonProperty("pg_provider")
    private String pgProvider;

}
