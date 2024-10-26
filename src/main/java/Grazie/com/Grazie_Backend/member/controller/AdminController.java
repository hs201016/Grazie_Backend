package Grazie.com.Grazie_Backend.member.controller;

import Grazie.com.Grazie_Backend.member.service.AdminCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
/*
    추후에 admin 패키지로 따로 뺄것.
 */
public class AdminController {

    private final AdminCheckService adminCheckService;

    // 관리자가 프로필 전체 조회
    @GetMapping("/read")
    public ResponseEntity<?> ReadUserProfile(Long userId) {
        return ResponseEntity.ok().body(adminCheckService.readUserInfo());
    }
}
