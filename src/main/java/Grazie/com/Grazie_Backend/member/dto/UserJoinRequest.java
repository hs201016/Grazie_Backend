package Grazie.com.Grazie_Backend.member.dto;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserJoinRequest {

    @NotEmpty(message = "사용자 ID는 필수입니다.")
    @Size(min = 4, max = 20, message = "사용자 ID는 4자 이상, 20자 이하이어야 합니다.")
    private String userId;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    @Size(min = 6, message = "비밀번호는 최소 6자 이상이어야 합니다.")
//     @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).+$", message = "비밀번호는 영문자와 숫자를 모두 포함해야 합니다.")
    private String password;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "전화번호는 필수입니다.")
    @Pattern(regexp = "^\\d{11}$", message = "전화번호는 11자리 숫자여야 합니다.")
    private String phone;
}