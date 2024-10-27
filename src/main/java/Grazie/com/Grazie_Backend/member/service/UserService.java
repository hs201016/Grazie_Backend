package Grazie.com.Grazie_Backend.member.service;

import Grazie.com.Grazie_Backend.Config.UserAdapter;
import Grazie.com.Grazie_Backend.member.dto.PasswordDTO;
import Grazie.com.Grazie_Backend.member.dto.TempPasswordRequest;
import Grazie.com.Grazie_Backend.member.dto.UserDTO;
import Grazie.com.Grazie_Backend.member.entity.PasswordToken;
import Grazie.com.Grazie_Backend.member.entity.User;
import Grazie.com.Grazie_Backend.member.enumpackage.Role;
import Grazie.com.Grazie_Backend.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public Long joinUser(User user) {
        String password = user.getPassword();
        String encodePassword = passwordEncoder.encode(password);
        user.setPassword(encodePassword);
        user.setRole(Role.CUSTOMER);
        userRepository.save(user);
        return user.getId();
    }

    private User getUserFromAdapter(UserAdapter userAdapter) {
        return userAdapter.getUser();
    }

    public User updatePassword(UserAdapter userAdapter, PasswordDTO passwordDTO) {
        User user = getUserFromAdapter(userAdapter);

        if (passwordDTO.getCurrentPassword() == null || passwordDTO.getNewPassword() == null) {
            System.out.println(passwordDTO.getCurrentPassword());
            System.out.println(passwordDTO.getNewPassword());
            throw new IllegalArgumentException("비밀번호는 null일 수 없습니다.");
        }

        // 현재 비밀번호가 올바른지 확인
        if (!passwordEncoder.matches(passwordDTO.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("현재 비밀번호가 올바르지 않습니다!");
        }

        user.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
        return userRepository.save(user);
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

    public UserDTO readUser(UserAdapter userAdapter) {
        User user = getUserFromAdapter(userAdapter);
        return new UserDTO(
                user.getEmail(),
                user.getName(),
                user.getPhone()
        );
    }

    public void deleteUser(UserAdapter userAdapter) {
        User user = getUserFromAdapter(userAdapter);
        userRepository.delete(user);
    }

    // 아이디 찾기
    public void findId(String email) {
    User user  = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("이메일 인증을 하는데 이런 이메일을 가진 유저가 없어요"));

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

    public void sendTempPassword(TempPasswordRequest request) {

    }
}