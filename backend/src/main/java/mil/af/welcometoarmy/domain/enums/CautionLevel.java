package mil.af.welcometoarmy.domain.enums;

public enum CautionLevel {

    NORMAL("정상"),
    INTEREST("관심병사");

    private String krName;

    CautionLevel(String krName) {
        this.krName = krName;
    }

    public String getKrName() {
        return krName;
    }
}
