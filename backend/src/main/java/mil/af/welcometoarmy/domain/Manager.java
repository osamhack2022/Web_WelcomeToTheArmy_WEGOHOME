package mil.af.welcometoarmy.domain;

import lombok.*;
import mil.af.welcometoarmy.domain.enums.Authority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class Manager extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String managerId;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String rank;

    @NotNull
    private String position;

    private String battalion;

    private String company;

    private String platoon;

    @NotNull
    private String phoneNumber;

    @NotNull
    private int logInFailCnt;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany(
            mappedBy = "manager",
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<Qna> qnaList = new ArrayList<>();

    @OneToMany(
            mappedBy = "manager",
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<Notice> noticeList = new ArrayList<>();

    public void setQnaList(List<Qna> qnaList) {
        this.qnaList = qnaList;
    }

    public void setNoticeList(List<Notice> noticeList) {
        this.noticeList = noticeList;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public void update(Manager manager) {
        managerId = manager.getManagerId();
        password = manager.getPassword();
        name = manager.getName();
        rank = manager.getRank();
        position = manager.getPosition();
        battalion = manager.getBattalion();
        company = manager.getCompany();
        platoon = manager.getPlatoon();
        phoneNumber = manager.getPhoneNumber();
        logInFailCnt = 0;
    }
}
