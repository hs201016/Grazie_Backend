package Grazie.com.Grazie_Backend.member.dto;

import Grazie.com.Grazie_Backend.member.enumpackage.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoRequest {
    @NotEmpty(message = "이메일은 필수입니다.")
    @Email(message = "유효한 이메일 주소를 입력하세요.")
    private String email;

    @NotEmpty(message = "이름은 필수입니다.")
    @Pattern(regexp = "^[가-힣]*$", message = "이름은 한글만 포함해야 합니다.")
    private String name;

    @NotEmpty(message = "전화번호는 필수입니다.")
    @Pattern(regexp = "^[0-9]*$", message = "전화번호는 숫자만 포함해야 합니다.")
    private String phone;

    @NotEmpty(message = "닉네임은 필수입니다.")
    private String nickname;

    private Gender gender;
}
