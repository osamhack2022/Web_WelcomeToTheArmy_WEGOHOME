package mil.af.welcometoarmy.domain.enums;

public enum Authority {

    ROLE_SOLDIER("훈련병", 1),
    ROLE_MANAGER("관리자", 2),
    ROLE_ADMINISTRATOR("최고관리자", 3);

    private String krName;
    private int level;

    Authority(String krName, int level) {
        this.krName = krName;
        this.level = level;
    }

    public String getKrName() {
        return krName;
    }

    public int getLevel() {
        return level;
    }
}
