package Grazie.com.Grazie_Backend.member.dto;

import Grazie.com.Grazie_Backend.member.entity.User;
import Grazie.com.Grazie_Backend.member.entity.UserAdditionalInfo;
import Grazie.com.Grazie_Backend.member.enumpackage.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
    private String email;
    private String name;
    private String phone;
    private  String nickname;
    private  String profileImage;
    private Gender gender;

    public UserInfoResponse(User user, UserAdditionalInfo userAdditionalInfo) {
            this.email = user.getEmail();
            this.name = user.getName();
            this.phone = user.getPhone();
            this.nickname = userAdditionalInfo.getNickname();
            this.profileImage = userAdditionalInfo.getProfileImage();
            this.gender = userAdditionalInfo.getGender();
        }
    }
