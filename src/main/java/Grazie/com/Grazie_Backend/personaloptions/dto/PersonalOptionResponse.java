package Grazie.com.Grazie_Backend.personaloptions.dto;

import Grazie.com.Grazie_Backend.personaloptions.enumfile.Concentration;
import Grazie.com.Grazie_Backend.personaloptions.enumfile.IceAddition;
import Grazie.com.Grazie_Backend.personaloptions.enumfile.TumblerUsage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonalOptionResponse {
    private Concentration concentration;
    private int shotAddition;
    private TumblerUsage personalTumbler;
    private int pearlAddition;
    private int syrupAddition;
    private int sugarAddition;
    private int whippedCreamAddition;
    private IceAddition iceAddition;
}
