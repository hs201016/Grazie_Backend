package Grazie.com.Grazie_Backend.member.dto;

import Grazie.com.Grazie_Backend.member.enumpackage.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminReadRequest {
    private Long userId;
    private String name;
    private String email;
    private String phone;
    private String nickname;
    private Gender gender;
}
