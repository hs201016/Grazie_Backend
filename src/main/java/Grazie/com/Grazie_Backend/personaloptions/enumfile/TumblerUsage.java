package Grazie.com.Grazie_Backend.personaloptions.enumfile;

public enum TumblerUsage {
    USE("사용함"),
    NOT_USE("사용 안함");
    private final String description;
    TumblerUsage(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
