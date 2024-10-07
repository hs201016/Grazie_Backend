package Grazie.com.Grazie_Backend.member.service;

import Grazie.com.Grazie_Backend.member.dto.AdminReadRequest;
import Grazie.com.Grazie_Backend.member.entity.User;
import Grazie.com.Grazie_Backend.member.entity.UserAdditionalInfo;
import Grazie.com.Grazie_Backend.member.repository.UserAdditionalInfoRepository;
import Grazie.com.Grazie_Backend.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminCheckService {

    private final UserRepository userRepository;
    private final UserAdditionalInfoRepository userAdditionalInfoRepository;

    public List<AdminReadRequest> readUserInfo() {
        List<User> users = userRepository.findAll();
        List<AdminReadRequest> adminReadRequests = new ArrayList<>();

        for (User user : users) {
            Optional<UserAdditionalInfo> optionalUserAdditionalInfo = userAdditionalInfoRepository.findByUserId(user.getId());

            AdminReadRequest adminReadRequest = new AdminReadRequest(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getPhone(),
                    optionalUserAdditionalInfo.map(UserAdditionalInfo::getNickname).orElse(null),
                    optionalUserAdditionalInfo.map(UserAdditionalInfo::getGender).orElse(null)
            );

            adminReadRequests.add(adminReadRequest);
        }

        return adminReadRequests;
    }
}
