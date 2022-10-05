package mil.af.welcometoarmy.domain.enums;

public enum Range {

    ALL("전체"),
    BATTALION("대대"),
    COMPANY("중대"),
    PLATOON("소대");

    private String krName;

    Range(String krName) {
        this.krName = krName;
    }

    public String getKrName() {
        return krName;
    }
}
