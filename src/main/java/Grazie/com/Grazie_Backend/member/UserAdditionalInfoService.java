package Grazie.com.Grazie_Backend.member;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UserAdditionalInfoService {

    private UserAdditionalInfoRepository userAdditionalInfoRepository;
    private UserRepository userRepository;

    private JwtUtil jwtUtil;


    public UserAdditionalInfoService(UserAdditionalInfoRepository userAdditionalInfoRepository,
                                     UserRepository userRepository, JwtUtil jwtUtil) {
        this.userAdditionalInfoRepository = userAdditionalInfoRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;


        File directory = new File(imageStorage);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private final String imageStorage = "storage/profile_images/";

    public UserAdditionalInfo saveAdditionalInfo(Long userId, UserAdditionalInfo userAdditionalInfo, MultipartFile profileImageFile) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userAdditionalInfo.setUser(user);

        if(profileImageFile != null && !profileImageFile.isEmpty()) {
            String imageName = saveImage(profileImageFile);
            userAdditionalInfo.setProfileImage(imageStorage + imageName);
        }
        return userAdditionalInfoRepository.save(userAdditionalInfo);
    }


    private String saveImage(MultipartFile profileImageFile) {
        try {
            String originalFilename = profileImageFile.getOriginalFilename();
            String uniqueFileName = System.currentTimeMillis() + "_" + originalFilename;
            Path filePath = Paths.get(imageStorage + uniqueFileName);
            Files.write(filePath, profileImageFile.getBytes());

            return uniqueFileName;
        } catch (IOException e) {
            throw new RuntimeException(e + "저장에 실패하였습니다.");
        }
    }

    public UserAdditionalInfo updateImage(String token, MultipartFile profileImageFile){
        try {
            Claims claims = jwtUtil.extractAllClaims(token);
            String userId = claims.getSubject();

            UserAdditionalInfo additionalInfo = userAdditionalInfoRepository.findByUserId(Long.valueOf(userId))
                    .orElseThrow(() -> new RuntimeException("user 추가정보를 찾을 수가 없습니다."));


            if (profileImageFile != null && !profileImageFile.isEmpty()) {
                String imageFile = saveImage(profileImageFile);
                additionalInfo.setProfileImage(imageFile);
            }
            return userAdditionalInfoRepository.save(additionalInfo);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
