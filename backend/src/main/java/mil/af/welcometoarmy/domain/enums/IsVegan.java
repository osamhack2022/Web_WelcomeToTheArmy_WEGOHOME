package mil.af.welcometoarmy.domain.enums;

public enum IsVegan {
    VEGAN("채식주의자"),
    NOT_VEGAN("비채식주의자");

    private String krName;

    IsVegan(String krName) {
        this.krName = krName;
    }

    public String getKrName() {
        return krName;
    }
}
