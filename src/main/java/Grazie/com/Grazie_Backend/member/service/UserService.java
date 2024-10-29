package Grazie.com.Grazie_Backend.member.service;

import Grazie.com.Grazie_Backend.Config.SecurityUtils;
import Grazie.com.Grazie_Backend.Config.UserAdapter;
import Grazie.com.Grazie_Backend.global.exception.AppException;
import Grazie.com.Grazie_Backend.member.dto.PasswordDTO;
import Grazie.com.Grazie_Backend.member.dto.UserDTO;
import Grazie.com.Grazie_Backend.member.dto.UserJoinRequest;
import Grazie.com.Grazie_Backend.member.entity.User;
import Grazie.com.Grazie_Backend.member.enumpackage.Role;
import Grazie.com.Grazie_Backend.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static Grazie.com.Grazie_Backend.global.util.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public Long joinUser(UserJoinRequest request) {
        User user = new User();
        user.setUserId(request.getUserId());
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPhone(request.getPhone());

        // 사용자 존재 여부 확인
        if (userRepository.existsByUserId(request.getUserId())) {
            throw new AppException(USERNAME_ALREADY_EXISTS.withArgs(request.getUserId()));
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(EMAIL_ALREADY_EXISTS.withArgs(request.getEmail()));
        }
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new AppException(PHONE_ALREADY_EXISTS.withArgs(request.getPhone()));
        }

        String encodePassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encodePassword);

        user.setRole(Role.CUSTOMER);
        userRepository.save(user);

        return user.getId();
    }


    private User getUserFromAdapter(UserAdapter userAdapter) {
        return userAdapter.getUser();
    }


    public User updatePassword(PasswordDTO passwordDTO) {
        UserAdapter currentUser = SecurityUtils.getCurrentUser();

        if (passwordDTO.getCurrentPassword() == null || passwordDTO.getNewPassword() == null) {
            throw new AppException(PASSWORD_NOT_NULL);
        }

        // 현재 비밀번호가 올바른지 확인
        if (!passwordEncoder.matches(passwordDTO.getCurrentPassword(), currentUser.getPassword())) {
            throw new AppException(INVALID_CURRENT_PASSWORD);
        }

        currentUser.getUser().setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
        return currentUser.getUser();
    }

    public User updateUser(UserAdapter userAdapter, UserDTO updateDTO) {
        User user = getUserFromAdapter(userAdapter);

        if (updateDTO.getPhone() != null) {
            user.setPhone(updateDTO.getPhone());
        }
        if (updateDTO.getEmail() != null) {
            user.setEmail(updateDTO.getEmail());
        }
        if (updateDTO.getName() != null) {
            user.setName(updateDTO.getName());
        }

        return userRepository.save(user);
    }

    public UserDTO readUser() {
        User user = SecurityUtils.getCurrentUser().getUser();
        return new UserDTO(
                user.getEmail(),
                user.getName(),
                user.getPhone()
        );
    }

    public void deleteUser() {
        User user = SecurityUtils.getCurrentUser().getUser();
        userRepository.delete(user);
    }

    // 아이디 찾기
    public void findId(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(EMAIL_NOT_FOUND));

        String subject = "아이디 찾기 안내입니다.";
        String text = "안녕하세요, 고객님!\n\n" +
                "요청하신 아이디 찾기 결과를 안내드립니다.\n\n" +
                "고객님의 아이디는\n" +
                "▶▶ " + user.getUserId() + " ◀◀\n\n" +
                "정확히 입력해 주시기 바랍니다.\n" +
                "감사합니다.\n\n" +
                "Grazie Service Team 드림.";

        emailService.sendEmail(user.getEmail(), subject, text);
    }
}