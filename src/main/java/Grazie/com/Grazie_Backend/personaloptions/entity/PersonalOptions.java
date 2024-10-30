package Grazie.com.Grazie_Backend.personaloptions.entity;


import Grazie.com.Grazie_Backend.personaloptions.enumfile.Concentration;
import Grazie.com.Grazie_Backend.personaloptions.enumfile.IceAddition;
import Grazie.com.Grazie_Backend.personaloptions.enumfile.TumblerUsage;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "personal_options")
@NoArgsConstructor
@EqualsAndHashCode
public class PersonalOptions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long optionId;
    @Column(name = "concentration", nullable = false)
    private Concentration concentration; // 농도 (연하게, 보통, 강하게)
    @Column(name = "shot_addition", nullable = false)
    private int shotAddition;      // 샷 추가 (0샷, 1샷, 2샷 이후로 무제한 추가 가능 )
    @Column(name = "personal_tumbler", nullable = false)
    private TumblerUsage personalTumbler;   // 개인 텀블러 사용 여부 (사용함, 사용 안함)
    @Column(name = "pearl_addition", nullable = false)
    private int pearlAddition;      // 펄 추가 (1번당 가격 추가)
    @Column(name = "syrup_addition", nullable = false)
    private int syrupAddition;      // 시럽 추가 (1번당 가격 추가)
    @Column(name = "sugar_addition", nullable = false)
    private int sugarAddition;      // 설탕 추가 여부 (1번당 가격 추가)
    @Column(name = "whipped_cream_addition", nullable = false)
    private int whippedCreamAddition; // 휘핑크림 추가 여부 (1번당 가격 추가)
    @Column(name = "ice_addition", nullable = false)
    private IceAddition iceAddition; // 얼음 추가 여부 (없음, 적게, 보통, 많이)

    public PersonalOptions(Concentration concentration, int shotAddition, TumblerUsage personalTumbler,
                           int pearlAddition, int syrupAddition, int sugarAddition,
                           int whippedCreamAddition, IceAddition iceAddition) {
        this.concentration = concentration;
        this.shotAddition = shotAddition;
        this.personalTumbler = personalTumbler;
        this.pearlAddition = pearlAddition;
        this.syrupAddition = syrupAddition;
        this.sugarAddition = sugarAddition;
        this.whippedCreamAddition = whippedCreamAddition;
        this.iceAddition = iceAddition;
    }
}