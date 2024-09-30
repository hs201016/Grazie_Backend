package Grazie.com.Grazie_Backend.Pay.controller;

import Grazie.com.Grazie_Backend.Pay.dto.MessageDTO;
import Grazie.com.Grazie_Backend.Pay.dto.PayResponseDTO;
import Grazie.com.Grazie_Backend.Pay.entity.Pay;
import Grazie.com.Grazie_Backend.Pay.service.PayService;
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
    public String paymentPage() {
        return "redirect:/portOnePay.html";
    }

    @PostMapping("/details/{imp_uid}")
    public ResponseEntity<PayResponseDTO> getPayDetails(@PathVariable(value = "imp_uid") String impUid) {
        PayResponseDTO response = payService.getPayDetails(impUid);
        return ResponseEntity.ok(response);
    }

    // 결제 취소 진행 및 DB 업데이트
    @DeleteMapping("/cancel/{imp_uid}")
    public ResponseEntity<MessageDTO> cancelPay(@PathVariable(value = "imp_uid") String impUid) {

        MessageDTO dto = payService.processCancelPay(impUid);
        return ResponseEntity.ok(dto);
    }

    // 결제 검증 및 DB 저장
    @PostMapping("/verify/{imp_uid}")
    public ResponseEntity<PayResponseDTO> verifyPay(@PathVariable(value = "imp_uid") String impUid) {
        PayResponseDTO response = payService.processPay(impUid);
        return ResponseEntity.ok(response);
    }

    //
    @GetMapping("/get/list/{name}")
    private ResponseEntity<List<Pay>> getAllPayByName(@PathVariable(value = "name") String buyerName) {
        List<Pay> payList = payService.getAllPayByBuyerName(buyerName);
        return ResponseEntity.ok(payList);
    }
}
