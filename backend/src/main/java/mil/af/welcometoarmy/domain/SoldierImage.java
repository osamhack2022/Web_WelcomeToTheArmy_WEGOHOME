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
public class SoldierImage {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String fileName;

    @NotNull
    private String filePath;

    @NotNull
    private String fileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOLDIER_ID", nullable = false)
    private Soldier soldier;

    public void setSoldier(Soldier soldier) {
        if (this.soldier != null)
            this.soldier.getImgFiles().remove(this);
        if (soldier == null) this.soldier = null;
        else {
            this.soldier = soldier;
            if (soldier.getImgFiles() == null) {
                List<SoldierImage> list = new ArrayList<>();
                list.add(this);
                soldier.setImgFiles(list);
            } else soldier.getImgFiles().add(this);
        }
    }
}
