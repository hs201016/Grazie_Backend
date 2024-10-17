package Grazie.com.Grazie_Backend.personaloptions.enumfile;

public enum Concentration {
    LIGHT("연하게"),
    NORMAL("보통"),
    STRONG("진하게");

    private final String description;

    Concentration(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}