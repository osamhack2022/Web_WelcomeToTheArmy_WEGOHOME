package mil.af.welcometoarmy.domain;

import lombok.*;
import mil.af.welcometoarmy.domain.enums.Authority;
import mil.af.welcometoarmy.domain.enums.CautionLevel;
import mil.af.welcometoarmy.domain.enums.IsVegan;
import mil.af.welcometoarmy.web.dto.soldier.SoldierResponseDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class Soldier extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String platoonNum;

    @NotNull
    private String password;

    @NotNull
    private LocalDate birthday;

    @Positive
    private int generation;

    @NotNull
    private String belong;

    @NotNull
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CautionLevel cautionLevel;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Authority authority;

    private String disease;

    private String phoneNumber;

    private String homeTel;

    private String uniqueness;

    @NotNull
    @Enumerated(EnumType.STRING)
    private IsVegan isVegan;

    private String hasAllergy;

    @NotNull
    private int point;

    @NotNull
    private int logInFailCnt;

    private String profilePicturePath;

    @OneToMany(
            mappedBy = "soldier",
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<Qna> qnaList = new ArrayList<>();

    public void setPassword(String password) {
        this.password = password;
    }

    public void setQnaList(List<Qna> qnaList) {
        this.qnaList = qnaList;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public void setLogInFailCnt(int logInFailCnt) {
        this.logInFailCnt = logInFailCnt;
    }

    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }

    public void update(Soldier soldier) {
        platoonNum = soldier.getPlatoonNum();
        password = soldier.getPassword();
        birthday = soldier.getBirthday();
        generation = soldier.getGeneration();
        belong = soldier.getBelong();
        name = soldier.getName();
        cautionLevel = soldier.getCautionLevel();
        disease = soldier.getDisease();
        phoneNumber = soldier.getPhoneNumber();
        homeTel = soldier.getHomeTel();
        uniqueness = soldier.getUniqueness();
        isVegan = soldier.getIsVegan();
        hasAllergy = soldier.getHasAllergy();
        logInFailCnt = 0;
    }

    public SoldierResponseDto toDto() {
        return SoldierResponseDto.builder()
                .id(id)
                .platoonNum(platoonNum)
                .birthday(birthday)
                .generation(generation)
                .belong(belong)
                .name(name)
                .cautionLevel(cautionLevel.name())
                .authority(authority.name())
                .disease(disease)
                .phoneNumber(phoneNumber)
                .homeTel(homeTel)
                .uniqueness(uniqueness)
                .isVegan(isVegan.name())
                .hasAllergy(hasAllergy)
                .point(point)
                .build();
    }
}
