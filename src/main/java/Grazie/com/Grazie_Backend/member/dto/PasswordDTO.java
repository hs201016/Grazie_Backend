package Grazie.com.Grazie_Backend.member.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PasswordDTO {

    @NotEmpty(message = "현재 비밀번호는 필수입니다.")
    private String currentPassword;

    @NotEmpty(message = "새 비밀번호는 필수입니다.")
//    @Size(min = 6, message = "새 비밀번호는 최소 6자 이상이어야 합니다.")
//    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).+$", message = "새 비밀번호는 영문자와 숫자를 모두 포함해야 합니다.")
    private String newPassword;

    public PasswordDTO() {
    }


    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

}
