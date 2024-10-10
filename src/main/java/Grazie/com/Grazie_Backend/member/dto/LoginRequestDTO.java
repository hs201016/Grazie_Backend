package Grazie.com.Grazie_Backend.member.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class LoginRequestDTO {
    @NotEmpty(message = "사용자 ID는 필수입니다.")
//    @Size(min = 4, max = 20, message = "사용자 ID는 4자 이상, 20자 이하이어야 합니다.")
    private String userid;

    @NotEmpty(message = "비밀번호는 필수입니다.")
//    @Size(min = 6, message = "비밀번호는 최소 6자 이상이어야 합니다.")
//    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).+$", message = "비밀번호는 영문자와 숫자를 모두 포함해야 합니다.")
    private String password;
}
