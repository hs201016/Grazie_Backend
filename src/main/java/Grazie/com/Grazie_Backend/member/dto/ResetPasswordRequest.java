package Grazie.com.Grazie_Backend.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
    private String token;
    private String tempPassword;
    private String newPassword;
}
