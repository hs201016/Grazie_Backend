package Grazie.com.Grazie_Backend.member.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDTO {

    @NotEmpty(message = "이메일은 필수입니다.")
    @Email(message = "유효한 이메일 주소를 입력하세요.")
    private String email;

    @NotEmpty(message = "이름은 필수입니다.")
    @Size(min = 2, max = 50, message = "이름은 2자 이상, 50자 이하이어야 합니다.")
    @Pattern(regexp = "^[가-힣]*$", message = "이름은 한글만 포함해야 합니다.")
    private String name;

    @NotEmpty(message = "전화번호는 필수입니다.")
    @Size(min = 10, max = 15, message = "전화번호는 10자 이상, 15자 이하이어야 합니다.")
    @Pattern(regexp = "^[0-9]*$", message = "전화번호는 숫자만 포함해야 합니다.")
    private String phone;



    public String getEmail() {
        return email;
    }


    public String getName() {
        return name;
    }


    public String getPhone() {
        return phone;
    }


    public UserDTO(String email, String name, String phone) {
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

    public UserDTO() {
    }
}
