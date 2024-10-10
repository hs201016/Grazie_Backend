package Grazie.com.Grazie_Backend.member.dto;

import Grazie.com.Grazie_Backend.member.enumpackage.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoRequest {
    private String email;
    private String name;
    private String phone;
    private String nickname;
    private Gender gender;
}
