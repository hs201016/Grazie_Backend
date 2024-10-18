package Grazie.com.Grazie_Backend.personaloptions.service;

import Grazie.com.Grazie_Backend.personaloptions.entity.PersonalOptions;
import org.springframework.stereotype.Service;

@Service
public class PersonalOptionPricingService {
    public int calculateAdditionalPrice(PersonalOptions personalOptions) {
        int additionalPrice = 0;

        // 샷 추가에 따른 가격 증가
        if (personalOptions.getShotAddition() != 0) {
            int shotCount = (personalOptions.getShotAddition());
            additionalPrice += shotCount * 500;  // 샷당 500원 추가
        }

        // 개인 텀블러 사용 여부에 따른 할인 (-300원) // 프론트에서 기본값을 사용 안함으로 설정
        if ("USE".equals(personalOptions.getPersonalTumbler())) {
            additionalPrice -= 300;
        }

        // 펄 추가에 따른 가격 증가
        if (personalOptions.getPearlAddition() != 0) {
            additionalPrice += (personalOptions.getPearlAddition()) * 700;  // 펄 추가시 700원 추가
        }

        // 시럽 추가에 따른 가격 증가
        if (personalOptions.getSyrupAddition() != 0) {
            additionalPrice += (personalOptions.getSyrupAddition()) * 200;  // 시럽 추가시 200원 추가
        }

        // 설탕 추가에 따른 가격 증가
        if (personalOptions.getSugarAddition() != 0) {
            additionalPrice += (personalOptions.getSugarAddition()) * 200;  // 설탕 추가시 200원 추가
        }

        // 휘핑크림 추가에 따른 가격 증가
        if (personalOptions.getWhippedCreamAddition() != 0) {
            additionalPrice += (personalOptions.getWhippedCreamAddition()) * 700;  // 휘핑크림 추가시 700원 추가
        }

        return additionalPrice;
    }
}

