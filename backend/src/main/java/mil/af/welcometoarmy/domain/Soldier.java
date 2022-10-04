package mil.af.welcometoarmy.domain;

import lombok.*;
import mil.af.welcometoarmy.domain.enums.CautionLevel;
import mil.af.welcometoarmy.domain.enums.IsVegan;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class Soldier {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String platoonNum;

    @NotNull
    private String password;

    @NotNull
    private LocalDate birthday;

    @NotNull
    private int generation;

    @NotNull
    private String battalion; // 대대

    @NotNull
    private String company; // 중대

    @NotNull
    private String platoon; // 소대

    @NotNull
    private String name;

    @Enumerated(EnumType.STRING)
    private CautionLevel cautionLevel;

    private String disease;

    private String phoneNumber;

    private String uniqueness;

    @Enumerated(EnumType.STRING)
    private IsVegan isVegan;

    private String hasAllergy;

    private int point;

    @OneToMany(
            mappedBy = "soldier",
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<SoldierImage> imgFiles = new ArrayList<>();

    public void setImgFiles(List<SoldierImage> imgFiles) {
        this.imgFiles = imgFiles;
    }
}
