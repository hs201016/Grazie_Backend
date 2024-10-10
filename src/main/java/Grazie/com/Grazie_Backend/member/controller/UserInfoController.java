package Grazie.com.Grazie_Backend.member.controller;

import Grazie.com.Grazie_Backend.Config.SecurityUtils;
import Grazie.com.Grazie_Backend.Config.UserAdapter;
import Grazie.com.Grazie_Backend.member.dto.UserInfoRequest;
import Grazie.com.Grazie_Backend.member.dto.UserInfoResponse;
import Grazie.com.Grazie_Backend.member.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-info")
@RequiredArgsConstructor
public class UserInfoController {
    private final UserInfoService userInfoService;

    @GetMapping
    public ResponseEntity<UserInfoResponse> getUserInfo() {
        UserAdapter userAdapter = SecurityUtils.getCurrentUser();
        UserInfoResponse user = userInfoService.getUser(userAdapter);
        return ResponseEntity.ok().body(user);
    }

    @PatchMapping
    public ResponseEntity<?> updateUserInfo(@RequestBody UserInfoRequest request) {
        UserAdapter userAdapter = SecurityUtils.getCurrentUser();
        UserInfoRequest result = userInfoService.updateUser(userAdapter, request);
        return ResponseEntity.ok().body(result);
    }
}
