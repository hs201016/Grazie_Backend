package Grazie.com.Grazie_Backend.member.dto;

public class PasswordDTO {

    private String currentPassword;
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
