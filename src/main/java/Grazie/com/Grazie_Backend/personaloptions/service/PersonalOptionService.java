package Grazie.com.Grazie_Backend.personaloptions.service;

import Grazie.com.Grazie_Backend.cart.dto.CartItemRequest;
import Grazie.com.Grazie_Backend.cart.dto.CartRequest;
import Grazie.com.Grazie_Backend.personaloptions.entity.PersonalOptions;
import Grazie.com.Grazie_Backend.personaloptions.enumfile.TumblerUsage;
import org.springframework.stereotype.Service;

@Service
public class PersonalOptionService {


    public PersonalOptions createPersonalOptions(CartItemRequest cartRequest) {

        return new PersonalOptions(
                cartRequest.getPersonalOptions().getConcentration(),
                cartRequest.getPersonalOptions().getShotAddition(),
                cartRequest.getPersonalOptions().getPersonalTumbler(),
                cartRequest.getPersonalOptions().getPearlAddition(),
                cartRequest.getPersonalOptions().getSyrupAddition(),
                cartRequest.getPersonalOptions().getSugarAddition(),
                cartRequest.getPersonalOptions().getWhippedCreamAddition(),
                cartRequest.getPersonalOptions().getIceAddition()
        );
    }

}
