package Grazie.com.Grazie_Backend.Pay.controller;

import Grazie.com.Grazie_Backend.Pay.dto.MessageDTO;
import Grazie.com.Grazie_Backend.Pay.dto.PayResponseDTO;
import Grazie.com.Grazie_Backend.Pay.entity.Pay;
import Grazie.com.Grazie_Backend.Pay.service.PayService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller // 페이지 렌더링을 위해 RestController -> Controller
@RequestMapping("/api/pay")
public class PayController {

    private final PayService payService;

    @Autowired
    public PayController(PayService payService) {
        this.payService = payService;
    }

    @GetMapping("/page")
    @Operation(summary = "결제 페이지", description = "결제 페이지로 리다이렉트합니다.")
    public String paymentPage() {
        return "redirect:/portOnePay.html";
    }

    @PostMapping("/details/{imp_uid}")
    @Operation(summary = "결제 상세 정보 조회", description = "주어진 imp_uid에 대한 결제 상세 정보를 반환합니다.")
    public ResponseEntity<PayResponseDTO> getPayDetails(@PathVariable(value = "imp_uid") String impUid) {
        PayResponseDTO response = payService.getPayDetails(impUid);
        return ResponseEntity.ok(response);
    }

    // 결제 취소 진행 및 DB 업데이트
    @DeleteMapping("/cancel/{imp_uid}")
    @Operation(summary = "결제 취소", description = "주어진 imp_uid에 대한 결제를 취소합니다.")
    public ResponseEntity<MessageDTO> cancelPay(@PathVariable(value = "imp_uid") String impUid) {

        MessageDTO dto = payService.processCancelPay(impUid);
        return ResponseEntity.ok(dto);
    }

    // 결제 진행, 검증 및 DB 저장
    @PostMapping("/progress/{imp_uid}")
    @Operation(summary = "결제 진행", description = "주어진 imp_uid에 대한 결제를 진행 및 검증합니다.")
    public ResponseEntity<PayResponseDTO> verifyPay(@RequestParam(value = "imp_uid") String impUid,
                                                    @RequestParam(value = "orderId") Long orderId) {
        PayResponseDTO response = payService.processPay(impUid, orderId);
        return ResponseEntity.ok(response);
    }

    //
    @GetMapping("/get/list/{name}")
    @Operation(summary = "구매자 이름으로 결제 내역 조회", description = "주어진 구매자 이름에 대한 결제 내역을 조회합니다.")
    private ResponseEntity<List<Pay>> getAllPayByName(@PathVariable(value = "name") String buyerName) {
        List<Pay> payList = payService.getAllPayByBuyerName(buyerName);
        return ResponseEntity.ok(payList);
    }
}
