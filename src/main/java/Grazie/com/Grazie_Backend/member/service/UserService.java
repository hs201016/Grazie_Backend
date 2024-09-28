package Grazie.com.Grazie_Backend.member.service;

import Grazie.com.Grazie_Backend.Config.JwtUtil;
import Grazie.com.Grazie_Backend.Config.UserAdapter;
import Grazie.com.Grazie_Backend.member.dto.PasswordDTO;
import Grazie.com.Grazie_Backend.member.dto.UserDTO;
import Grazie.com.Grazie_Backend.member.entity.User;
import Grazie.com.Grazie_Backend.member.enumpackage.Role;
import Grazie.com.Grazie_Backend.member.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public User joinUser(User user) {
        String password = user.getPassword();
        String encodePassword = passwordEncoder.encode(password);
        user.setPassword(encodePassword);
        user.setRole(Role.CUSTOMER);
        return userRepository.save(user);
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
}