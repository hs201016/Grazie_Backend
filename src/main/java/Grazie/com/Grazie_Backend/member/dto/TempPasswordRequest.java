package Grazie.com.Grazie_Backend.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TempPasswordRequest {
    private String userId;
    private String email;
}
