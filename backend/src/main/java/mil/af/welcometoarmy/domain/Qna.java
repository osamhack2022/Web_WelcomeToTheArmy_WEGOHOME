package mil.af.welcometoarmy.domain;

import lombok.*;
import mil.af.welcometoarmy.domain.enums.HasAnswer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class Qna {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String questionTitle;

    @NotNull
    private String questionContent;

    private String answerTitle;

    private String answerContent;

    @NotNull
    @Enumerated(EnumType.STRING)
    private HasAnswer hasAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOLDIER_ID", nullable = false)
    private Soldier soldier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANAGER_ID")
    private Manager manager;

    public void setSoldier(Soldier soldier) {
        if (this.soldier != null)
            this.soldier.getQnaList().remove(this);
        if (soldier == null) this.soldier = null;
        else {
            this.soldier = soldier;
            if (soldier.getQnaList() == null) {
                List<Qna> list = new ArrayList<>();
                list.add(this);
                soldier.setQnaList(list);
            } else soldier.getQnaList().add(this);
        }
    }

    public void setManager(Manager manager) {
        if (this.manager != null)
            this.manager.getQnaList().remove(this);
        if (manager == null) this.manager = null;
        else {
            this.manager = manager;
            if (manager.getQnaList() == null) {
                List<Qna> list = new ArrayList<>();
                list.add(this);
                manager.setQnaList(list);
            } else manager.getQnaList().add(this);
        }
    }
}
