package Grazie.com.Grazie_Backend.member.service;

import Grazie.com.Grazie_Backend.Config.UserAdapter;
import Grazie.com.Grazie_Backend.member.dto.UserDTO;
import Grazie.com.Grazie_Backend.member.dto.UserInfoRequest;
import Grazie.com.Grazie_Backend.member.dto.UserInfoResponse;
import Grazie.com.Grazie_Backend.member.entity.User;
import Grazie.com.Grazie_Backend.member.entity.UserAdditionalInfo;
import Grazie.com.Grazie_Backend.member.enumpackage.Gender;
import Grazie.com.Grazie_Backend.member.service.UserAdditionalInfoService;
import lombok.RequiredArgsConstructor;
import org.junit.runners.Parameterized;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserAdditionalInfoService userAdditionalInfoService;

    public UserInfoResponse getUser(UserAdapter userAdapter) {
        User user = userAdapter.getUser();
        System.out.println(user);
        UserAdditionalInfo userAdditionalInfo = userAdditionalInfoService.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("추가 정보가 존재하지 않습니다."));
        return new UserInfoResponse(user, userAdditionalInfo);
    }

    public UserInfoRequest updateUser(UserAdapter userAdapter, UserInfoRequest request) {
        User user = userAdapter.getUser();
        UserAdditionalInfo userAdditionalInfo = userAdditionalInfoService.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("추가 정보가 존재하지 않습니다."));

        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getName() != null) user.setName(request.getName());

        if (request.getNickname() != null) {
            userAdditionalInfo.setNickname(request.getNickname());
        } if (request.getGender() != null) {
            try {
                userAdditionalInfo.setGender(Gender.valueOf(String.valueOf(request.getGender())));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("유효하지 않은 성별 값입니다.");
            }
        }
        return request;
    }
}
