package Grazie.com.Grazie_Backend.member.dto;



public class UserDTO {

    private String email;
    private String name;
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
