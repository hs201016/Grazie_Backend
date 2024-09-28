package Grazie.com.Grazie_Backend.member.service;

import Grazie.com.Grazie_Backend.Config.JwtUtil;
import Grazie.com.Grazie_Backend.Config.UserAdapter;
import Grazie.com.Grazie_Backend.member.dto.UserAdditionalInfoDTO;
import Grazie.com.Grazie_Backend.member.entity.User;
import Grazie.com.Grazie_Backend.member.entity.UserAdditionalInfo;
import Grazie.com.Grazie_Backend.member.enumpackage.Gender;
import Grazie.com.Grazie_Backend.member.repository.UserAdditionalInfoRepository;
import Grazie.com.Grazie_Backend.member.repository.UserRepository;
import Grazie.com.Grazie_Backend.member.service.ImageStorageService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserAdditionalInfoService {

    private final UserAdditionalInfoRepository userAdditionalInfoRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final ImageStorageService imageStorageService;

    @Autowired
    public UserAdditionalInfoService(UserAdditionalInfoRepository userAdditionalInfoRepository,
                                     UserRepository userRepository, JwtUtil jwtUtil, ImageStorageService imageStorageService) {
        this.userAdditionalInfoRepository = userAdditionalInfoRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.imageStorageService = imageStorageService;
    }


    public UserAdditionalInfo saveAdditionalInfo(Long userId, UserAdditionalInfo userAdditionalInfo, MultipartFile profileImageFile) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userAdditionalInfo.setUser(user);

        if(profileImageFile != null && !profileImageFile.isEmpty()) {

            String imageName = imageStorageService.saveImage(profileImageFile);
            String imageStorage = imageStorageService.imageStorage;
            userAdditionalInfo.setProfileImage(imageStorage + imageName);
        }
        return userAdditionalInfoRepository.save(userAdditionalInfo);
    }


    public UserAdditionalInfoDTO readAdditionalInfo(UserAdapter userAdapter) {
        Long userId = userAdapter.getUser().getId();
        UserAdditionalInfo userAdditionalInfo = getUserAdditionalInfoByUserId(userId);


        return new UserAdditionalInfoDTO(
                userAdditionalInfo.getNickname(),
                userAdditionalInfo.getProfileImage(),
                userAdditionalInfo.getGender()
        );
    }

    public UserAdditionalInfo updateAdditionalInfo(UserAdapter userAdapter, UserAdditionalInfoDTO updateDTO) {
        UserAdditionalInfo userAdditionalInfo = getUserAdditionalInfoByUserId(userAdapter.getUser().getId());

        if (updateDTO.getGender() != null) {
            try {
                userAdditionalInfo.setGender(Gender.valueOf(String.valueOf(updateDTO.getGender())));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("유효하지 않은 성별 값입니다.");
            }
        }
        if (updateDTO.getNickname() != null) {
            userAdditionalInfo.setNickname(updateDTO.getNickname());
        }
        return userAdditionalInfoRepository.save(userAdditionalInfo);
    }

    public void deleteAdditionalInfo(UserAdapter userAdapter) {
        Long userId = userAdapter.getUser().getId();
        UserAdditionalInfo userAdditionalInfo = getUserAdditionalInfoByUserId(userId);
        userAdditionalInfoRepository.delete(userAdditionalInfo);
    }

    private UserAdditionalInfo getUserAdditionalInfoByUserId(Long userId) {
        return userAdditionalInfoRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("유저 추가정보를 찾을 수 없습니다."));
    }
}
