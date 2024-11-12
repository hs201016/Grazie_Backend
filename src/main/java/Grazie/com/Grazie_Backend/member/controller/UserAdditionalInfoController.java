package Grazie.com.Grazie_Backend.member.controller;

import Grazie.com.Grazie_Backend.Config.SecurityUtils;
import Grazie.com.Grazie_Backend.Config.UserAdapter;
import Grazie.com.Grazie_Backend.member.dto.UpdateNicknameRequest;
import Grazie.com.Grazie_Backend.member.dto.UserAdditionalInfoDTO;
import Grazie.com.Grazie_Backend.member.entity.UserAdditionalInfo;
import Grazie.com.Grazie_Backend.member.service.ImageStorageService;
import Grazie.com.Grazie_Backend.member.service.UserAdditionalInfoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/users/additional-info")
@RestController
@RequiredArgsConstructor
public class UserAdditionalInfoController {

    private final ImageStorageService imageStorageService;
    private final UserAdditionalInfoService userAdditionalInfoService;
    private final ObjectMapper objectMapper;


    @PostMapping("/{userId}/additionalInfoJoin")
    public ResponseEntity<UserAdditionalInfo> additionalInfoJoin(
            @PathVariable("userId") Long userId,
            @RequestParam("additionalInfo") String additionalInfoJson,
            @RequestPart("profileImage") MultipartFile profileImageFile) throws JsonProcessingException {
        UserAdditionalInfo userAdditionalInfo = objectMapper.readValue(additionalInfoJson, UserAdditionalInfo.class);
        UserAdditionalInfo saveAdditionalInfo = userAdditionalInfoService.saveAdditionalInfo(userId, userAdditionalInfo, profileImageFile);
        return ResponseEntity.ok(saveAdditionalInfo);
    }


    @GetMapping("/readProfile")
    public ResponseEntity<?> readUserProfile() {
        UserAdapter userAdapter = SecurityUtils.getCurrentUser();
        UserAdditionalInfoDTO userAdditionalInfo = userAdditionalInfoService.readAdditionalInfo(userAdapter);
        return ResponseEntity.ok(userAdditionalInfo);
    }

    // 사진을 제외하고 업데이트
    @PatchMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestBody UserAdditionalInfoDTO userAdditionalInfoDTO) {
        UserAdapter userAdapter = SecurityUtils.getCurrentUser();
        UserAdditionalInfo userAdditionalInfo = userAdditionalInfoService.updateAdditionalInfo(userAdapter, userAdditionalInfoDTO);
        return ResponseEntity.ok(userAdditionalInfo);
    }

    @PatchMapping("/updateNickname")
    public ResponseEntity<UserAdditionalInfo> updateNickname(@RequestBody UpdateNicknameRequest updateNicknameRequest) {
        UserAdditionalInfo userAdditionalInfo = userAdditionalInfoService.updateNickname(updateNicknameRequest);
        return ResponseEntity.ok(userAdditionalInfo);
    }


    @PatchMapping("/updateProfileImage")
    public ResponseEntity<?> updateProfileImage(@RequestPart("profileImage") MultipartFile profileImageFile) {
        UserAdapter userAdapter = SecurityUtils.getCurrentUser();
        UserAdditionalInfo additionalInfo = imageStorageService.updateImage(userAdapter, profileImageFile);
        return ResponseEntity.ok(additionalInfo);
    }
}
