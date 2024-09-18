package Grazie.com.Grazie_Backend.Pay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller // 페이지 렌더링을 위해 RestController -> Controller
@RequestMapping("/api/pay")
public class PayController {

    @GetMapping("/page")
    public String paymentPage(Model model) {
        model.addAttribute("merchantId", "imp23488700"); // 가맹점 식별코드
        model.addAttribute("amount", 10000);
        model.addAttribute("orderId", "ORDER20230918001");

        return "payment";
    }
}
