package mil.af.welcometoarmy.domain.enums;

public enum Authority {

    ROLE_SOLDIER("훈련병"),
    ROLE_MANAGER("관리자"),
    ROLE_ADMINISTRATOR("최고관리자");

    private String krName;

    Authority(String krName) {
        this.krName = krName;
    }

    public String getKrName() {
        return krName;
    }
}
