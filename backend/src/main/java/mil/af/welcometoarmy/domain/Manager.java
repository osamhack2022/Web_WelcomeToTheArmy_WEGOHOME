package mil.af.welcometoarmy.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class Manager {

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

    @NotNull
    private String battalion;

    private String company;

    private String platoon;

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
}
