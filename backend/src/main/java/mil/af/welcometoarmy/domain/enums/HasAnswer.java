package mil.af.welcometoarmy.domain.enums;

public enum HasAnswer {

    ANSWER_DONE("답변 완료"),
    ANSWER_INCOMPLETE("답변 미완료");

    private String krName;

    HasAnswer(String krName) {
        this.krName = krName;
    }

    public String getKrName() {
        return krName;
    }
}
