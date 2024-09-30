package Grazie.com.Grazie_Backend.member.dto;


import Grazie.com.Grazie_Backend.member.enumpackage.Gender;
import lombok.Setter;

@Setter
public class UserAdditionalInfoDTO {

    private  String nickname;
    private  String profileImage;
    private  Gender gender;

    public UserAdditionalInfoDTO(String nickname, String profileImage, Gender gender) {
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.gender = gender;
    }

    public UserAdditionalInfoDTO() {
    }

    public Gender getGender() {
        return gender;
    }
    public String getNickname() {
        return nickname;
    }
    public String getProfileImage() {
        return profileImage;
    }


}

