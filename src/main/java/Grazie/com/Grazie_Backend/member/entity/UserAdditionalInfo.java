package Grazie.com.Grazie_Backend.member.entity;

import Grazie.com.Grazie_Backend.member.enumpackage.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserAdditionalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "nickname")
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    private User user;

    public UserAdditionalInfo(String profileImage, String nickname, Gender gender) {
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.gender = gender;
    }

    public UserAdditionalInfo() {

    }
}
