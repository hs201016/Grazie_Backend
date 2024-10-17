package Grazie.com.Grazie_Backend.personaloptions.enumfile;

public enum IceAddition {
    NONE("없음"),
    LESS("적게"),
    NORMAL("보통"),
    MORE("많이");

    private final String description;

    IceAddition(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
