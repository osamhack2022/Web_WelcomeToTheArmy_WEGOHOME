package mil.af.welcometoarmy.domain;

import lombok.*;
import mil.af.welcometoarmy.domain.enums.Authority;
import mil.af.welcometoarmy.web.dto.manager.ManagerResponseDto;

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

    @NotNull
    private String belong;

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

    public void setQnaList(List<Qna> qnaList) {
        this.qnaList = qnaList;
    }


    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public void setLogInFailCnt(int logInFailCnt) {
        this.logInFailCnt = logInFailCnt;
    }

    public void update(Manager manager) {
        managerId = manager.getManagerId();
        password = manager.getPassword();
        name = manager.getName();
        rank = manager.getRank();
        position = manager.getPosition();
        belong = manager.getBelong();
        phoneNumber = manager.getPhoneNumber();
        logInFailCnt = 0;
    }

    public ManagerResponseDto toDto() {
        return ManagerResponseDto.builder()
                .id(id)
                .managerId(managerId)
                .name(name)
                .rank(rank)
                .position(position)
                .belong(belong)
                .phoneNumber(phoneNumber)
                .authority(authority.name())
                .build();
    }


}
